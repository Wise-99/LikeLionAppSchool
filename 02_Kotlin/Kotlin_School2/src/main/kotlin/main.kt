import java.util.*
import kotlin.collections.ArrayList

fun main() {
    var school = School()
    school.inputStudent()
    school.printStudentInfo()
    school.totalScore()
}

class School{
    val scan = Scanner(System.`in`)
    val stuList = ArrayList<Student>()
    var koreanTotal:Int = 0
    var englishTotal:Int = 0
    var mathTotal:Int = 0

    fun inputStudent(){
        while(true){
            println("학생 정보를 입력해주세요.")
            print("이름 : ")
            var name = scan.next()
            print("국어 : ")
            var korean = scan.nextInt()
            print("영어 : ")
            var english = scan.nextInt()
            print("수학 : ")
            var math = scan.nextInt()

            // 객체 생성
            var stu = Student(name, korean, english, math).apply {
                total = korean + english + math
                avg = total / 3
            }
            stuList.add(stu)

            print("계속 입력하시겠습니까?(1. 예, 2. 아니오) : ")
            var num = scan.nextInt()
            if (num == 2){
                break
            }
        }
    }
    fun printStudentInfo(){
        for(stu in stuList){
            println("이름 : ${stu.name}")
            println("국어 : ${stu.korean}")
            println("영어 : ${stu.english}")
            println("수학 : ${stu.math}")
            println("총점 : ${stu.total}")
            println("평균 : ${stu.avg}")
        }
    }
    fun totalScore(){
        for (stu in stuList){
            koreanTotal += stu.korean
            englishTotal += stu.english
            mathTotal += stu.math
        }
        printScore(koreanTotal, englishTotal, mathTotal){ koreanT: Int, englishT:Int, mathT:Int ->
            println("국어 전체 총점 : ${koreanT}점")
            println("수학 전체 총점 : ${mathT}점")
            println("영어 전체 총점 : ${englishT}점")
            println("국어 전체 평균 : ${koreanT / stuList.size}점")
            println("영어 전체 평균 : ${englishT / stuList.size}점")
            println("수학 전체 평균 : ${mathT / stuList.size}점")
        }
    }

    fun printScore(korean:Int, english:Int, math:Int, m1: (Int, Int, Int) -> Unit){
        m1(korean, english, math)
    }
}

class Student(var name:String, var korean:Int, var english:Int, var math:Int){
    var total = 0
    var avg = 0
}