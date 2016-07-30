object LRU(val size: Int) {
  def apply(size: Int) = new LRU(size)

  val keyToIdx = new scala.collection.mutable.HashMap[String, Int](size) // key -> index
  val idxToVal = scala.collection.mutalbe.ArrayBuffer.empty[Any] // index -> value

  // makes 'key' entry 'hot'
  def get(key: String): T = {
    
  }

  // kicks off oldest entry if over size
  def set(key: String, val: T): Unit = {
    if keyToIdx.contains(key) {
      val idx = keyToIdx.get(key)
      val value = idxToVal(idx)
      swapWithFirst(idx)
      keyToIdx.put(key, 0)
      value
    } else {
      if (idxToVal.size > size) {
        idxToVal.last       
      } else {
   
      }
    }
  }
}