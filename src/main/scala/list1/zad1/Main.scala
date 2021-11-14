import scala.collection.immutable.ListMap
import scala.collection.mutable.ListBuffer

object Main {

  def main(args: Array[String]): Unit = {
    val docs_list = List[(String, String)](
        ("example1", "../resources/example1.txt"),
        ("example2", "../resources/example2.txt"),
        ("Harry Potter and the Philosopher's stone", "../resources/harry_potter_1.txt")
    )
    val stop_words_filename = "../resources/stop_words.txt"
    val document_handler = new Document_Handler(docs_list, stop_words_filename)
    val documents = document_handler.load_docs
    get_most_frequent_in_all_docs(50, documents)
    // get_most_frequent_in_docs(50, 1, documents)

    // val tfidf = new TF_IDF(documents)
    // tfidf.get_TF_IDF_map.foreach(println(_))

  }

  private def get_most_frequent_in_docs(n: Int, documentInd: Int, documents: List[Document]): Unit = {
    val document = documents(documentInd)
    val list_map_count = ListMap(document.words_count_map
      .toSeq.sortWith(_._2 > _._2):_*)
    list_map_count.take(n).foreach(p => println(p._1 + " " + p._2))
  }
  private def get_most_frequent_in_all_docs(n: Int, documents: List[Document]): Unit = {
    println(Console.GREEN + Console.BOLD + n + " most frequent words in all documents" + Console.RESET)
    documents.foreach(document => {
      println(Console.BLUE + Console.BOLD + f"Document: ${document.document_name}" + Console.RESET)
      ListMap(document.words_count_map
        .toSeq.sortWith(_._2 > _._2):_*)
        .take(n).foreach(p => println(f"${p._1} == ${p._2}"))
    })
  }
}
