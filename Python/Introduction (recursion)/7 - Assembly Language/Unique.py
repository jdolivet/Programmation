def unique(L):
  """return True if that list has only unique elements 
  (no elements repeated) and False otherwise."""
  if L == []:
    return True
  else:
    return (L[0] not in L[1:]) and unique(L[1:])
#print(unique([1, 2, 3]))
#print(unique([2, 42, 3, 42]))
