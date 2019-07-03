package com.mkopec.ticketbookingapp.service;

import com.mkopec.ticketbookingapp.domain.RoomSeat;
import com.mkopec.ticketbookingapp.exception.ArgumentNotValidException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class SeatValidationService {
    private enum SeatType {
        FREE_INSIDE,
        FREE_ON_EDGE,
        OCCUPIED,
        RESERVATION_DONE
    }

    private static class SeatModel {
        private SeatType seatType;
        private RoomSeat roomSeat;
        private boolean reserved = false;

        private SeatModel(RoomSeat roomSeat, SeatType seatType) {
            this.seatType = seatType;
            this.roomSeat = roomSeat;
        }

        String getRow() {
            return roomSeat.getSeat().getRow();
        }

        Integer getNumber() {
            return roomSeat.getSeat().getNumber();
        }
    }

    private static final int NUM_OF_NEIGHBOURS = 2;

    boolean validate(List<RoomSeat> reservedSeats, List<RoomSeat> occupiedSeats, List<RoomSeat> allSeats) throws ArgumentNotValidException {
        Map<RoomSeat, SeatModel> seats = initializeSeatModels(reservedSeats, occupiedSeats, allSeats);

        // sorting allows to take neighbour of seat
        Map<String, List<SeatModel>> groupedSeatModelsToRows = seats.values().stream()
                .sorted(Comparator.comparing(SeatModel::getRow).thenComparing(SeatModel::getNumber))
                .collect(groupingBy(SeatModel::getRow));

        for (Map.Entry<String, List<SeatModel>> entry : groupedSeatModelsToRows.entrySet()) {
            List<SeatModel> models = entry.getValue();
            for (int i = 0; i < models.size(); i++) {
                SeatModel model = models.get(i);
                if (model.reserved) {
                    // check if is possible to reserve
                    switch (model.seatType) {
                        case FREE_ON_EDGE: {
                            model.seatType = SeatType.RESERVATION_DONE;
                            break;
                        }
                        case FREE_INSIDE: {
                            List<SeatModel> neighbourhood = getNeighbourhoodOfSeat(models, i);
                            if (isReservationValidForSeatModel(neighbourhood, model)) {
                                model.seatType = SeatType.RESERVATION_DONE;
                            } else {
                                throw new ArgumentNotValidException("Dont left single seat near seat: " + model.roomSeat.getId());
                            }
                            break;
                        }
                    }
                }
            }
        }

        return true;
    }

    private Map<RoomSeat, SeatModel> initializeSeatModels(List<RoomSeat> reservedSeats, List<RoomSeat> occupiedSeats, List<RoomSeat> allSeats) {
        Map<RoomSeat, SeatModel> seats = new HashMap<>();

        allSeats.forEach(rs -> seats.put(rs, new SeatModel(rs, rs.getIsOnEdge() ? SeatType.FREE_ON_EDGE : SeatType.FREE_INSIDE)));
        occupiedSeats.forEach(rs -> {
            if (seats.containsKey(rs)) {
                seats.put(rs, new SeatModel(rs, SeatType.OCCUPIED));
            }
        });
        reservedSeats.forEach(rs -> {
            if (seats.containsKey(rs)) {
                SeatModel seatModel = seats.get(rs);
                if (!seatModel.seatType.equals(SeatType.OCCUPIED)) {
                    seatModel.reserved = true;
                } else {
                    throw new ArgumentNotValidException("Seat with ID: " + seatModel.roomSeat.getId() + " is already reserved");
                }
            }
        });
        return seats;
    }

    private List<SeatModel> getNeighbourhoodOfSeat(List<SeatModel> models, int index) {
        int lastElementIndex = models.size() - 1;

        int fromIndex = index < NUM_OF_NEIGHBOURS ? 0 : index - NUM_OF_NEIGHBOURS;
        int toIndex = index > lastElementIndex - NUM_OF_NEIGHBOURS ? lastElementIndex : index + 1 + NUM_OF_NEIGHBOURS;

        return models.subList(fromIndex, toIndex);
    }

    private boolean isReservationValidForSeatModel(List<SeatModel> neighbourhood, SeatModel model) {
        int modelIndex = neighbourhood.indexOf(model);
        boolean isFreeSeatLeft = false;

        if (hasPrevious(modelIndex)) {
            SeatModel p = getPrevious(neighbourhood, modelIndex);
            switch (p.seatType) {
                case FREE_INSIDE: {
                    if (hasPrevious(modelIndex - 1)) {
                        SeatModel pp = getPrevious(neighbourhood, modelIndex - 1);
                        switch (pp.seatType) {
                            case FREE_INSIDE:
                            case FREE_ON_EDGE:
                                isFreeSeatLeft = false;
                                break;
                            case OCCUPIED:
                            case RESERVATION_DONE:
                                isFreeSeatLeft = true;
                                break;
                        }
                    }
                    break;
                }
                case FREE_ON_EDGE:
                    isFreeSeatLeft = true;
                    break;
                case OCCUPIED:
                case RESERVATION_DONE:
                    return true;
            }
        }

        if (isFreeSeatLeft) {
            if (hasNext(neighbourhood, modelIndex)) {
                SeatModel n = getNext(neighbourhood, modelIndex);
                switch (n.seatType) {
                    case FREE_INSIDE:
                    case FREE_ON_EDGE:
                        return false;
                    case OCCUPIED:
                    case RESERVATION_DONE:
                        break;
                }
            }
        } else {
            if (hasNext(neighbourhood, modelIndex)) {
                SeatModel n = getNext(neighbourhood, modelIndex);
                switch (n.seatType) {
                    case FREE_INSIDE: {
                        if (hasNext(neighbourhood, modelIndex + 1)) {
                            SeatModel nn = getNext(neighbourhood, modelIndex + 1);
                            switch (nn.seatType) {
                                case FREE_INSIDE:
                                case FREE_ON_EDGE:
                                    break;
                                case OCCUPIED:
                                case RESERVATION_DONE:
                                    return false;
                            }
                        }
                        break;
                    }
                    case FREE_ON_EDGE:
                    case OCCUPIED:
                    case RESERVATION_DONE:
                        break;
                }
            }
        }

        return true;
    }

    private SeatModel getNext(List<SeatModel> neighbourhood, int index) {
        return neighbourhood.get(index + 1);
    }

    private SeatModel getPrevious(List<SeatModel> neighbourhood, int index) {
        return neighbourhood.get(index - 1);
    }

    private boolean hasPrevious(int index) {
        return index - 1 >= 0;
    }

    private boolean hasNext(List<SeatModel> neighbourhood, int index) {
        return index + 1 < neighbourhood.size();
    }
}
