# python 2
#
# Homework 10, Problem 1
#
# Name: Johann
#

class Date:
    """ a user-defined data structure that
        stores and manipulates dates
    """

    # the constructor is always named __init__ !
    def __init__(self, month, day, year):
        """ the constructor for objects of type Date """
        self.month = month
        self.day = day
        self.year = year

    # the "printing" function is always named __repr__ !
    def __repr__(self):
        """ This method returns a string representation for the
            object of type Date that calls it (named self).

             ** Note that this _can_ be called explicitly, but
                it more often is used implicitly via the print
                statement or simply by expressing self's value.
        """
        s =  "%02d/%02d/%04d" % (self.month, self.day, self.year)
        return s
    # here is an example of a "method" of the Date class:
    def isLeapYear(self):
        """ Returns True if the calling object is
            in a leap year; False otherwise. """
        if self.year % 400 == 0: return True
        elif self.year % 100 == 0: return False
        elif self.year % 4 == 0: return True
        return False
        
    def copy(self):
        """ Returns a new object with the same month, day, year
            as the calling object (self).
        """
        dnew = Date(self.month, self.day, self.year)
        return dnew

    def equals(self, d2):
        """ Decides if self and d2 represent the same calendar date,
            whether or not they are the in the same place in memory.
        """
        if self.year == d2.year and self.month == d2.month and self.day == d2.day:
            return True
        else:
            return False
            
    def tomorrow(self):
      """not return anything
      change the calling object so that it represents 
      one calendar day after the date it originally represented"""
      fdays = 28 + self.isLeapYear()
      DIM = [0,31,fdays,31,30,31,30,31,31,30,31,30,31]
      if self.day == DIM[self.month]:
        self.day = 1
        if self.month <= 11:
          self.month += 1
        else:
          self.month = 1
          self.year += 1
      else:
        self.day += 1
      
    def yesterday(self):
      """not return anything
      change the calling object so that it represents 
      one calendar day before the date it originally represented"""
      fdays = 28 + self.isLeapYear()
      DIM = [0,31,fdays,31,30,31,30,31,31,30,31,30,31]
      if self.day == 1:
        if DIM[self.month - 1] != 0:
          self.day = DIM[self.month - 1]
        else:
          self.day = 31
        if self.month >= 2:
          self.month -= 1
        else:
          self.month = 12
          self.year -= 1
      else:
        self.day -= 1
        
    def addNDays(self, N):
      """change the calling object so that it represents 
      N calendar days after the date it originally represented."""
      print self
      for i in range(N):
        self.tomorrow()
        print self
        
    def subNDays(self, N):
      """change the calling object so that it represents 
      N calendar days before the date it originally represented."""
      print self
      for i in range(N):
        self.yesterday()
        print self
        
    def isBefore(self, d2):
      """return True if the calling object is a 
      calendar date before the argument named d2 
      (which will always be an object of type Date). 
      If self and d2 represent the same day, 
      this method should return False. 
      Similarly, if self is after d2, this should return False."""
      if self.year > d2.year:
        return False
      elif self.year < d2.year:
        return True
      else:
        if self.month > d2.month:
          return False
        elif self.month < d2.month:
          return True
        else:
          if self.day >= d2.day:
            return False
          else:
            return True
            
    def isAfter(self, d2):
      """return True if the calling object is a 
      calendar date after the argument named d2 
      (which will always be an object of type Date). 
      If self and d2 represent the same day, 
      this method should return False. 
      Similarly, if self is before d2, this should return False."""
      if self.equals(d2):
        return False
      elif self.isBefore(d2):
        return False
      else:
        return True
      
    def diff(self, d2):
      """should return an integer representing 
      the number of days between self and d2"""
      cp = 0
      if self.isBefore(d2):
        mul = -1
        early = self.copy()
        late = d2.copy()
      else:
        mul = 1
        early = d2.copy()
        late = self.copy()
      while early.equals(late) == False:
        early.tomorrow()
        cp += 1
      return cp * mul
      
    def dow(self):
      """return a string that indicates the day of the week (dow) 
      of the object (of type Date) that calls it. 
      That is, this method returns one of the following strings: 
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", or "Sunday"."""
      weekDay = {0:"Monday", 1:"Tuesday", 2:"Wednesday", 3:"Thursday", 4:"Friday", 5:"Saturday", 6: "Sunday"}
      today = Date(9,5,2016) #monday
      nb = self.diff(today)
      return weekDay[nb%7]
      

### test for tomorrow
##d = Date(12, 31, 2014)
##print(d)
##d.tomorrow()
##print(d)
##d = Date(2, 28, 2016)
##print(d)
##d.tomorrow()
##print(d)
##d.tomorrow()
##print(d)
##
### test for yesterday
##d = Date(1, 1, 2015)
##print(d)
##d.yesterday()
##print(d)
##d = Date(3, 1, 2016)
##print(d)
##d.yesterday()
##print(d)
##d.yesterday()
##print(d)
##
### test for addN
##d = Date(11, 12, 2014)
##print(d)
##d.addNDays(3)
##print(d)
##d = Date(11, 12, 2014)
##print(d)
##d.addNDays(1278)
##print(d)

## test for isBefore and after
##ny = Date(1,1,2015)
##d2 = Date(11,12,2014)
##print(ny.isBefore(d2))
##print(d2.isBefore(ny))
##print(d2.isBefore(d2))
##print(ny.isAfter(d2))
##print(d2.isAfter(ny))
##print(d2.isAfter(d2))

##test for diff
##d = Date(11,12,2014)
##d2 = Date(12,19,2014)
##print(d2.diff(d))
##print(d.diff(d2))
##print(d)
##print(d2)
##d = Date(12,1,2015)
##d3 = Date(3,15,2016)
##print(d3.diff(d))
##d = Date(11, 12, 2014)
##print(d.diff(Date(1, 1, 1899)))

#d = Date(12, 7, 1941)
#print(d.dow())
#print(Date(10, 28, 1929).dow())
#print(Date(1, 1, 2100).dow())
