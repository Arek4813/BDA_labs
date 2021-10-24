import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.io.Source

object solution {

  def text_input(given_string: String, original_map: mutable.Map[String, Int], lines_stopwords: List[String]): mutable.Map[String, Int] = {

    val sublist = new ListBuffer[String]()
    val list: List[String] = given_string.split(' ').toList
    for (subelement <- list) {
      val cleaned_subelement = subelement.replace(",", "").replace(".", "").replace(":", "").replace("[", "").replace("]", "")
      if (cleaned_subelement.nonEmpty) {
        sublist += cleaned_subelement
      }
    }
    val real_list = sublist.toList
    val list_without_stopwords = real_list diff lines_stopwords

    val count_map = list_without_stopwords.groupBy(identity).mapValues(_.size)

    val new_map = original_map ++ count_map.map { case (k, v) => k -> (v + original_map.getOrElse(k, 0)) }

    new_map
  }

  def text_input_file(given_file: String, original_map: mutable.Map[String, Int], lines_stopwords: List[String]): mutable.Map[String, Int] = {

    val sublist = new ListBuffer[String]()
    val lines = Source.fromFile(given_file).getLines.toList
    for(element<-lines){
      val small_list: List[String] = element.split(' ').toList
      for(subelement<-small_list) {
        val cleaned_subelement = subelement.replace(",", "").replace(".", "").replace(":", "").replace("[", "").replace("]", "")
        if (cleaned_subelement.nonEmpty) {
          sublist += cleaned_subelement
        }
      }
    }
    val real_list = sublist.toList
    val list_without_stopwords = real_list diff lines_stopwords

    val count_map = list_without_stopwords.groupBy(identity).mapValues(_.size)

    val new_map = original_map ++ count_map.map { case (k, v) => k -> (v + original_map.getOrElse(k, 0)) }

    new_map
  }

  def word_cloud_output_console(original_map: mutable.Map[String, Int], n:Int): Unit = {
    println(original_map.toSeq.sortBy(_._2)(Ordering[Int].reverse).take(n).toMap.keys)
  }

  def word_cloud_output_to_file(original_map: mutable.Map[String, Int], n:Int): Unit ={
    val csv_file = "word_cloud.csv"
    val bw = new BufferedWriter(new FileWriter(csv_file))
    for(el_to_write<-original_map.toSeq.sortBy(_._2)(Ordering[Int].reverse).take(n).toMap.keys){
      bw.write(el_to_write)
      bw.write("\n")
    }
    bw.close()
  }

  def main(args: Array[String]) = {

    var my_map = collection.mutable.Map[String, Int]()

    val filename_stopwords = "stop_words_english.txt"
    val lines_stopwords = Source.fromFile(filename_stopwords).getLines.toList

    while(true){
      println(my_map)
      println("Choose what you want to do:")
      println("1) Give pure string")
      println("2) Give file")
      println("3) Print selected number of most frequent words")
      println("4) Write selected number of most frequent words to csv file")
      val decision = scala.io.StdIn.readLine().toInt
      if(decision == 1){
        println("1) Give pure string:")
        my_map = text_input(scala.io.StdIn.readLine().toString, my_map, lines_stopwords)
      }
      if(decision == 2){
        println("2) Give path to string file:")
        my_map = text_input_file(scala.io.StdIn.readLine().toString, my_map, lines_stopwords)
      }
      if(decision == 3){
        println("3) Give number:")
        word_cloud_output_console(my_map, scala.io.StdIn.readLine().toInt)
      }
      if(decision == 4){
        println("4) Give number:")
        word_cloud_output_to_file(my_map, scala.io.StdIn.readLine().toInt)
      }
    }

  }
}