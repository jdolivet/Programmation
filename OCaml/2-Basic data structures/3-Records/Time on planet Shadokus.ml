let wellformed date =
  if date.year >= 1 && 
     date.month >= 1 && date.month <= 5 &&
     date.day >= 1 && date.day <= 4 &&
     date.hour >= 0 && date.hour <= 2 &&
     date.minute >=0 && date.minute <= 1
  then true else false
;;

let next date = 
  let rest_min = (date.minute + 1) / 2 in
  let rest_hour = (date.hour + rest_min) / 3 in
  let rest_day = (date.day + rest_hour) / 5 in
  let rest_month = (date.month + rest_day) / 6 in
  { year = (date.year + rest_month); 
    month = (date.month + rest_day - 1) mod 5 + 1; 
    day = (date.day + rest_hour - 1) mod 4 + 1;
    hour = (date.hour + rest_min) mod 3; 
    minute = (date.minute + 1) mod 2 
  }
;; 

let rec of_int minutes = 
  if minutes = 0 then the_origin_of_time else
    next (of_int (minutes - 1))
;;
