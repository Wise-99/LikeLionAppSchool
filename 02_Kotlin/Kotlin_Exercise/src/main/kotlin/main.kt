import java.io.EOFException
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.util.Calendar
import java.util.Scanner

fun main() {
    val mainClass = MainClass()
    mainClass.running()
}

// 메인 클래스
class MainClass{
    val scanner = Scanner(System.`in`)
    val exerList = ArrayList<Exercise>()
    val setDate = mutableSetOf<String>()

    // 프로그램 상태 전체를 관리하며 운영하는 메서드
    fun running(){

        while(true) {

            // 프로그램 상태에 따른 분기
            when (ChangeToState(mainMenu())) {
                ProgramState.PROGRAM_STATE_EXERCISE_WRITE -> {
                    readObjectStream()
                    write()
                }

                ProgramState.PROGRAM_STATE_EXERCISE_READ -> {
                    readObjectStream()
                    printDateExercise(printDateList())
                }

                ProgramState.PROGRAM_STATE_EXERCISE_END -> {
                    break
                }
            }
        }
        println("프로그램이 종료되었습니다.")
    }

    fun mainMenu() : Int{
        var number = 0

        while (true){
            println()
            println("""메뉴를 선택해주세요
            |1. 오늘의 운동 기록
            |2. 날짜별 운동 기록 보기
            |3. 종료
        """.trimMargin())
            print("번호 입력 : ")
            number = scanner.nextInt()

            if (number > 3 || number < 1){
                println("다시 입력해주세요")
                continue
            } else {
                break
            }
        }
        return number
    }

    fun ChangeToState(number:Int): ProgramState {
        var pro : ProgramState = ProgramState.PROGRAM_STATE_EXERCISE_WRITE

        when(number){
            1 -> {pro = ProgramState.PROGRAM_STATE_EXERCISE_WRITE}
            2 -> {pro = ProgramState.PROGRAM_STATE_EXERCISE_READ}
            3 -> {pro = ProgramState.PROGRAM_STATE_EXERCISE_END}
        }

        return pro
    }

    fun write() {
        println()
        print("운동 종류 : ")
        val type = scanner.next()
        print("횟수 : ")
        val time = scanner.nextInt()
        print("세트 : ")
        val set = scanner.nextInt()

        //val exer1 = Exercise("23", 1,1)
        //exer1.date = "2023-05-23"

        //val exer2 = Exercise("22", 1,1)
        //exer2.date = "2023-05-22"

        val exer = Exercise(type, time, set)

        exerList.add(exer)
        //exerList.add(exer1)
        //exerList.add(exer2)

        saveObjectStream(exerList)
    }

    fun saveObjectStream(exerList: ArrayList<Exercise>){
        val fos = FileOutputStream("exercise.dat")
        val oos = ObjectOutputStream(fos)

        for (e in exerList){
            oos.writeObject(e)
        }

        oos.flush()
        oos.close()
        fos.close()
    }

    fun readObjectStream() {
        val fis = FileInputStream("exercise.dat")
        val ois = ObjectInputStream(fis)
        exerList.clear()

        try {
            while (true){
                var e1 = ois.readObject() as Exercise
                exerList.add(e1)
                setDate.add(e1.date)
            }
        } catch (e:EOFException){

        }

        ois.close()
        fis.close()
    }

    fun printDateList(): String {
        val setList = setDate.toList()

        for (i in 0 until(setList.size)){
            println("${i+1}. ${setList[i]}")
        }

        println()
        print("날짜를 선택해주세요(0. 이전 ) : ")
        var number = scanner.nextInt()

        if (number != 0){
            return setList[number-1]
        }

        return ""
    }

    fun printDateExercise(date:String){
        for (e in exerList){
            if(e.date == date){
                println()
                e.printExercise()
            }
        }
    }
}

// 프로그램 상태를 나타내는 enum
enum class ProgramState(num: Int) {
    // 상태를 나타내는 값들을 정의한다.
    PROGRAM_STATE_EXERCISE_WRITE(1),
    PROGRAM_STATE_EXERCISE_READ(2),
    PROGRAM_STATE_EXERCISE_END(3)


}

class Exercise(val type:String, val time:Int, val set:Int) : Serializable{
    var date : String

    init {
        var c1 = Calendar.getInstance()
        val year = c1.get(Calendar.YEAR).toString()
        val month = c1.get(Calendar.MONTH) + 1
        val day = c1.get(Calendar.DAY_OF_MONTH)

        this.date = "$year-0$month-$day"
    }

    fun printExercise(){
        println("운동 타입 : $type")
        println("횟수 : $time")
        println("세트 : $set")
    }
}
