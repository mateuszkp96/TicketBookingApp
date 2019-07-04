#!/usr/bin/env bash
generate_post_data()
{
cat <<EOF
{
   "screeningID": "$screening_id",
   "firstName": "$first_name",
   "lastName": "$last_name",
   "tickets": [
      {
         "seatID": "$seat_1",
         "ticketTypeID": "$type_2"
      },
      {
         "seatID": "$seat_2",
         "ticketTypeID": "$type_3"
      },
      {
         "seatID": "$seat_3",
         "ticketTypeID": "$type_1"
      },
      {
         "seatID": "$seat_4",
         "ticketTypeID": "$type_1"
      }
   ]
}
EOF
}


passed_port=$1
default_port=8080
port=${passed_port:-$default_port}

host="localhost:$port"

selected_date=`date --date="next day" +%Y-%m-%d`
time_start="14:00:00"
time_end="22:00:00"

list_movies_endpoint="$host/screening?date=$selected_date&timeStart=$time_start&timeEnd=$time_end"

echo "Selected date: $selected_date"
echo "Time interval start: $time_start"
echo "Time interval end: $time_end"
echo "The endpoint: $list_movies_endpoint"

curl -v ${list_movies_endpoint} 

screening_id=2
echo "Selected screening: $screening_id"

curl -v "$host/screening/$screening_id" 


first_name="Ola"
last_name="Kalinowska-Jaworska"
type_1=1
type_2=2
type_3=3

seat_1=1
seat_2=2
seat_3=11
seat_4=12



reservation_endpoint="$host/reservation"
echo "Post reservation request body"
echo $(generate_post_data) 

curl -v \
    -H 'Content-Type: application/json' \
    -d "$(generate_post_data)" \
    ${reservation_endpoint} 

