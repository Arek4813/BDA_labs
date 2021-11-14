import scala.collection.mutable.ListBuffer

class TF_IDF(val docs: List[Document]) {

  def get_TF_IDF_map: Map[String, Map[String, Double]] = {
    val tf_values = compute_TF
    val idf_values = compute_IDF
    val tf_idf = collection.mutable.Map[String, Map[String, Double]]()
    docs.foreach(document => {
      val document_TF = tf_values(document.document_name)
      tf_idf += (document.document_name -> document.words_count_map.map(p => (p._1, document_TF(p._1) * idf_values(p._1))))
    })
    tf_idf.toMap
  }

  private def compute_TF: collection.mutable.Map[String, Map[String, Double]] = {
    val tf_values = collection.mutable.Map[String, Map[String, Double]]()
    docs.foreach(document => {
      val words_count = document.words_count_map
      tf_values += (document.document_name -> words_count.map(p => (p._1, p._2.asInstanceOf[Double] / document.all_words_list.length)))
    })
    tf_values
  }

  def compute_IDF: Map[String, Double] = {
    val set_of_documents_words = ListBuffer[Set[String]]()
    docs.foreach(document => {
      set_of_documents_words += (document.all_words_list.toSet)
    })
    val all_words_set = collection.mutable.Set[String]()
    docs.foreach(document => {
      document.all_words_list.toSet.foreach(all_words_set.add)
    })
    all_words_set.map(word => (word, Math.log(docs.length / get_no_of_occurrences(word, set_of_documents_words)))).toMap
  }

  private def get_no_of_occurrences(p: String, word_sets: ListBuffer[Set[String]]): Int = {
    var occurrences = 0
    word_sets.foreach(set => {
      if (set.contains(p))
        occurrences += 1
    })
    occurrences
  }


}
