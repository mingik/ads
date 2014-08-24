def firstOcc(String s) {
  if (s.length() <= 1)
    s
  else {
    Map<Character,Integer> lhm = new LinkedHashMap<Character,Integer>()

    for (char c in s) {
      if (lhm.containsKey(c))
	lhm.put(c, lhm.get(c) + 1)
	else
	lhm.put(c,1)
    }

    for (entry in lhm.entrySet()) {
      if (entry.getValue() == 1)
	return entry.getKey()
    }

    return null    
  }
}

println firstOcc("abaabbcskajweoij")