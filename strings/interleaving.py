
# Given two strings, print all the inter-leavings of the Strings in which characters from two strings should be in same order as they were in original strings. 

# e.g. 
# for "abc", "de", print all of these: 
# adebc, abdec, adbce, deabc, dabce, etc, etc

def interleaveHelper(str1, str2, idx1, idx2, acc):
    if (idx1 == len(str1) and idx2 == len(str2)):
        print(acc)
    if (idx1 < len(str1)):
        interleaveHelper(str1,str2,idx1+1,idx2,acc+str1[idx1])
    if (idx2 < len(str2)):
        interleaveHelper(str1,str2,idx1,idx2+1,acc+str2[idx2])

def interleave(str1, str2):
    return interleaveHelper(str1, str2, 0, 0, "")
