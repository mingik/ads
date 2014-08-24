## Problem: Given string: "asdfsdafweei" return the FIRST character that ocurrs only once in the string

## Solution: 
## 0.1) initialize array of bools corresponding to the characters in the string (initially false) -- occurMoreThanOnce[]
## 0.2) initialize an empty set -- alreadySeen
## 1) iterate (character,index) on given string:
##      if alreadySeen.contains(character):
##        occurMoreThanOnce[index] = true
##      else:
##        alreadySeen.add(character)
## 2) iterate (ind,boo) on occurMoreThanOnce:
##      if (boo is True):
##        remove character s[ind] from alreadySeen
## 3) iterate cha on s:
##      if (alreadySeen.contains(cha)):
##        return char

def firstOcurr(s):
    # assert s in not empty
    if len(s) == 1:
      return s # or s[0]
    else:
      occurMoreThanOnce = [False for i in s] # 0.1)
      alreadySeen = set() # 0.2)

      for ind,cha in enumerate(s): # 1)
        if cha in alreadySeen:
          occurMoreThanOnce[ind] = True
        else:
          alreadySeen.add( cha )

      for ind,boo in enumerate(occurMoreThanOnce): # 2)
        if boo is True:
          alreadySeen.remove(s[ind])
      
      for cha in s: # 3)
        if cha in alreadySeen:
          return cha
