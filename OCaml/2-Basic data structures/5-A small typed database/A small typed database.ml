let proof_of_bug = 
  [| 
    { code = 0; contact = { name = "luke"; phone_number = (4, 3, 2, 1) } } ;
    { code = 0; contact = { name = "darth"; phone_number = (4, 3, 2, 1) } } ; 
    { code = 1; contact = { name = "luke"; phone_number = (4, 3, 2, 1) } } ; 
    { code = 2; contact = { name = "darth"; phone_number = (4, 3, 2, 1) } } ;
  |] 
;; 

let delete db contact =
  let (status, db, contact) = search db contact in
  if not status then (false, db, contact)
  else
    let cells i = 
      if db.contacts.(i).name = contact.name then 
        db.contacts.(db.number_of_contacts - 1)
      else db.contacts.(i) 
    in 
    let db' = {
      number_of_contacts = db.number_of_contacts - 1;
      contacts = Array.init (Array.length db.contacts) cells
    }
    in
    (true, db', contact)
;;

let update db contact =
  let (status, db, _) = search db contact in
  if not status then insert db contact else 
    let cells i =
      if db.contacts.(i).name = contact.name then 
        contact
      else db.contacts.(i)
    in
    let db' = {
      number_of_contacts = db.number_of_contacts;
      contacts = Array.init (Array.length db.contacts) cells
    }
    in
    (true, db', contact)
;;

let engine db { code ; contact } =
  if code = 0 then insert db contact
  else if code = 1 then delete db contact
  else if code = 2 then search db contact
  else if code = 3 then update db contact
  else (false, db, nobody)
;;
