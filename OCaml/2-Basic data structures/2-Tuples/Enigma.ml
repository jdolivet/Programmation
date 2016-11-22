let exchange k =
  (k mod 10) * 10 + (k / 10) ;;

let is_valid_answer (grand_father_age, grand_son_age) =
  grand_son_age * 4 = grand_father_age 
  && 
  (exchange grand_father_age) * 3 = exchange grand_son_age ;;


let rec find answer =
  let (grand_father_age, grand_son_age) = answer in
  if grand_son_age >= grand_father_age then (-1,-1) else
  if find_down answer <> (-1,-1) then find_down answer else 
    find (grand_father_age, grand_son_age + 1)
and 
  find_down answer =
  let (grand_father_age, grand_son_age) = answer in
  if grand_son_age >= grand_father_age then (-1,-1) else
  if is_valid_answer answer = true then answer else 
    find_down (grand_father_age - 1, grand_son_age);;

