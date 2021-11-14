import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer
import scala.io.Source

class Document_Handler(val doc_list: List[(String, String)], val STOP_WORDS_FILENAME: String) {

  def load_docs = {
    load_words().toList
  }

  private def load_words(): ListBuffer[Document] = {
    val docs = ListBuffer[Document]()
    doc_list.foreach(doc_info => {
      var words_list = ListBuffer[String]()
      val source = Source.fromFile(doc_info._2)
      val document_lines = try source.getLines.toList finally source.close
      for (line <- document_lines) {
        val split = line.split(' ').toList
        for (words <- split) {
          val cleaned = remove_regex(words)
          if (cleaned.nonEmpty) words_list += words
        }
      }
      words_list = remove_stop_words(words_list)
      val wordsCount = count_words(words_list)
      docs += new Document(words_list.toList, wordsCount, doc_info._1)
    }
    )
    docs
  }

  private def remove_regex(words: String): String = {
    words.replaceAll("""[\p{Punct}&&[^.]]""", "")
  }

  private def remove_stop_words(words_list: ListBuffer[String]): ListBuffer[String] ={
    val source = Source.fromFile(STOP_WORDS_FILENAME)
    val stop_words_list = try source.getLines.toList finally source.close
    words_list.filter(!stop_words_list.contains(_))
  }

  private def count_words(words_list: ListBuffer[String]) = {
    Map(words_list.groupBy(w => w)
      .map(t => (t._1, t._2.length))
      .toSeq.sortWith(_._2 > _._2):_*)
  }

}
