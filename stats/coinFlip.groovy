def heads() {
  return Math.random() < 0.5
}

boolean[] results = new boolean[10]
for (int i = 0; i < results.length; i++) {
  if (heads()) results[i] = true
}

println results