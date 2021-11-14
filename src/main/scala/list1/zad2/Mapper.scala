import scala.collection.mutable.ListBuffer

class Mapper {

  def map(list: List[(Int, List[Int])]): List[(Int, Int)] = {
    var inv_edge_list = ListBuffer[(Int, Int)]()
    list.foreach(tuple => {
      inv_edge_list = inv_edge_list ++ map(tuple)
    })
    inv_edge_list.toList
  }

  def map(tuple: (Int, List[Int])): List[(Int, Int)] = {
    val inv_edge_list = ListBuffer[(Int, Int)]()
    tuple._2.foreach(edge => {
      inv_edge_list.append((edge, tuple._1))
    })
    inv_edge_list.toList
  }

}
