let rec gcd n m =
  if m = 0 then n else gcd m (n mod m);;

let rec multiple_upto n r =
  if r < 2 then false else
  if r = 2 || multiple_of n r = true then multiple_of n r else multiple_upto n (r - 1);;

let is_prime n =
  if n = 1 then false else
    not (multiple_upto n (integer_square_root n));;
