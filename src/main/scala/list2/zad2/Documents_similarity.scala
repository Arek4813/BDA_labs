import scala.collection.mutable.ListBuffer
import scala.io.Source

object Documents_similarity {

  def main(args: Array[String]): Unit = {
    println(generateKShingles(loadBook("sample5.txt"), 3))
    val bookAShingles = generateKShingles(loadBook("harry_potter_2.txt"), 5)
    val bookBShingles = generateKShingles(loadBook("harry_potter_3.txt"), 5)
    println(f"jaccard sim = ${jaccardSim(bookAShingles, bookBShingles)}")
  }

  def jaccardSim(bookA: Set[String], bookB: Set[String]) = {
    bookA.intersect(bookB).size.asInstanceOf[Float] / bookA.union(bookB).size // could be double
  }

  def generateKShingles(book: List[String], k: Int) = {
    val bookShinglesSet = collection.mutable.Set[String]()
    for (i <- 0 until book.length - k + 1) {
      val shingle = book.slice(i, i + k).mkString(" ")
      bookShinglesSet.add(shingle)
    }
    bookShinglesSet.toSet
  }

  def loadBook(title: String) = {
    val wordsList = ListBuffer[String]()
    val source = Source.fromFile(title)
    val lines = source.getLines.toList
    for (line <- lines) {
      val split = line.split(" ")
      for (word <- split) {
        val cleaned = word.replaceAll("\\.(?!.*\\.)", "")
        if (cleaned.nonEmpty) wordsList += cleaned.toLowerCase
      }
    }
    removeStopWords(wordsList).toList
  }

  def removeStopWords(wordsList: ListBuffer[String]) = {
    val source = Source.fromFile("stop_words.txt")
    val stopWordsList = try source.getLines.toList finally source.close
    wordsList.filter(!stopWordsList.contains(_))
  }
}
