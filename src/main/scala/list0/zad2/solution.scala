import java.io.{BufferedWriter, File, FileWriter}
import scala.io.Source
import scala.collection.mutable.ListBuffer

object solution {
  def main(args: Array[String]) = {

    val filename = "file.txt"
    val sublist = new ListBuffer[String]()
    val lines = Source.fromFile(filename).getLines.toList
    for(element<-lines){
      val small_list: List[String] = element.split(' ').toList
      for(subelement<-small_list) {
        val cleaned_subelement = subelement.replaceAll("[^A-Za-z0-9']", "")
        if (cleaned_subelement.nonEmpty) {
          sublist += cleaned_subelement
        }
      }
    }
    val real_list = sublist.toList

    val filename_stopwords = "stop_words_english.txt"
    val lines_stopwords = Source.fromFile(filename_stopwords).getLines.toList
    val list_without_stopwords = real_list diff lines_stopwords

    val count_map = list_without_stopwords.groupBy(identity).mapValues(_.size).toSeq.sortBy(_._2)(Ordering[Int].reverse)

    val wordcloud_filename = "wc_file.txt"
    val file = new File(wordcloud_filename)
    val bw = new BufferedWriter(new FileWriter(file))
    for(el_to_write<-count_map.take(100).toMap.keys){
      bw.write(el_to_write)
      bw.write("\n")
    }
    bw.close()

  }
}