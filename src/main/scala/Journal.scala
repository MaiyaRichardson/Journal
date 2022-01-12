import java.sql.DriverManager
import java.sql.Connection
import java.util.Scanner
import com.mysql.cj.xdevapi.UpdateStatement
import java.util.InputMismatchException
import java.io.File
import java.io.PrintWriter

object Journal {
   def main(args: Array[String]):Unit = {
     var scanner = new Scanner(System.in)

      // connect to the database named "mysql" on the localhost
      val driver = "com.mysql.jdbc.Driver"
      val url = "jdbc:mysql://localhost:3306/proj0" // Modify for whatever port you are running your DB on
      val username = "root"
      val password = "asdfgh3!" // Update to include your password
      val log = new PrintWriter(new File("Journal.log"))

      var connection:Connection = null

    try {
      // make the connection
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      // create the statement, and run the select query
     
      val statement = connection.createStatement()
      println("Connection Established To Server :)")
      println()

      println("Would you like to delete a previous entry? If so, press '1' and then enter in the ID for the entry you'd like to delete: " + "'\n Otherwise, please type 'Continue'")
      val resultSet2 = statement.executeQuery("SELECT * FROM Journal")

      println()
      while ( resultSet2.next() ) {
        print(resultSet2.getString(1) + " " + resultSet2.getString(2) + " " + resultSet2.getString(3) + " " + resultSet2.getString(4) + " " + resultSet2.getString(5) + " " + resultSet2.getString(6))
        println()
      }

      var input = scanner.next()
      var cont = "Continue"
      var rem = 1

      if (input.equals(cont)){
        println("Please enter an integer as your ID: ")
        var ID = scanner.nextInt()

        println("Enter in your first name: ")
        var FirstName = scanner.next()

        println("Enter in the month: ")
        var DateMonth = scanner.nextInt()

        println("Enter in the day: ")
        var DateDay = scanner.nextInt()

        println("Enter in the year: ")
        var DateYear = scanner.nextInt()
        scanner.nextLine()
        println("You are not able to write in your journal using more than 255 characters: ")
        var JournalPrompt = scanner.nextLine()
        
        val resultSet1 = statement.executeUpdate("INSERT INTO Journal (ID, FirstName, DateMonth, DateDay, DateYear, JournalPrompt) VALUES ("+ID+",'"+FirstName+"', "+DateMonth+", "+DateDay+", "+DateYear+", '"+JournalPrompt+"');")
        log.write("Executing 'INSERT INTO Journal (ID, FirstName, DateMonth, DateDay, DateYear, JournalPrompt) VALUES ("+ID+",'"+FirstName+"', "+DateMonth+", "+DateDay+", "+DateYear+", '"+JournalPrompt+"')';\n")
         
      }else if (input.trim.toInt == rem) {
        println("Please enter an integer as your ID: ")
        var ID = scanner.nextInt()
        val deleteSet = statement.executeUpdate("DELETE FROM Journal WHERE ID = '"+ID+"';")
        log.write("Executing 'DELETE FROM Journal WHERE ID = '"+ID+"'';\n")

      }else if (input.trim.toInt != rem){
          println("You did not enter in '1'. Please try again " +'\n')
          main(args: Array[String])
      }
      

       
      
      val resultSet = statement.executeQuery("SELECT * FROM Journal") // Change query to your table
      log.write("Executing 'SELECT * FROM Journal';\n")
      while ( resultSet.next() ) {
        print( resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5) + " " + resultSet.getString(6))
        println()
      }
    } catch {
      case e: Exception => e.printStackTrace
    }
    connection.close()

  }
}
