import java.util.Scanner
import kotlin.collections.ArrayList

fun main() {
    val school = School()
    school.inputStudent()
    school.studentAction()
}


// 일반, 축구부, 수영부, 배구부 입력받을 열거형
enum class Activity(var action: String) {
    ONE("일반"), TWO("축구부"), THREE("배구부"), FOUR("수영부")
}

// 학교 안 익명 중첩 클래스 생성
class School {
    val scan = Scanner(System.`in`)
    val studentList = ArrayList<Student>()

    // 학생 정보 입력받는 함수
    fun inputStudent(){
        var inputNumber = 0
        while(true){
            println("----- 학생 등록 -----")
            print("소속을 입력해주세요(1. 일반, 2. 축구부, 3.배구부, 4.수영부, 0.입력종료) : ")
            inputNumber = scan.nextInt()

            if(inputNumber !in 0..4){
                println("잘못 입력하였습니다")
                continue
            }

            if (inputNumber == 0){
                println("---------------------")
                break
            }

            print("이름 입력 : ")
            val name = scan.next()
            print("학년 입력 : ")
            val grade = scan.nextInt()
            print("국어 점수 입력 : ")
            val korean = scan.nextInt()
            print("영어 점수 입력 : ")
            val english = scan.nextInt()
            print("수학 점수 입력 : ")
            val math = scan.nextInt()

            // 학생 객체 생성
            val student = createStudent(inputNumber, name, grade, korean, english, math)
            studentList.add(student)
        }
    }

    // 학생 객체를 생성하는 함수(익명 중첩 클래스)
    fun createStudent(num:Int, name: String, grade: Int, korean: Int, english: Int, math: Int): Student {
        lateinit var stu :Student
        when(num){
            // 일반 학생
            1 -> {
                stu = object : Student(Activity.ONE.action, name, grade, korean, english, math) {
                    override fun study() {
                        printAction(Activity.ONE, name)
                    }

                    override fun printInfo() {
                        println("소속 : $team")
                        println("이름 : $name")
                        println("학년 : ${grade}학년")
                        println("국어 : ${korean}점")
                        println("영어 : ${english}점")
                        println("수학 : ${math}점")
                        printTotalAndAvg(korean, english, math)
                    }

                }
            }
            // 축구부 학생
            2 -> {
                stu = object : Student(Activity.TWO.action, name, grade, korean, english, math), Soccer, Swimming{
                    override fun study() {
                        printAction(Activity.ONE, name)
                    }

                    override fun printInfo() {
                        println("소속 : $team")
                        println("이름 : $name")
                        println("학년 : ${grade}학년")
                        println("국어 : ${korean}점")
                        println("영어 : ${english}점")
                        println("수학 : ${math}점")
                        printTotalAndAvg(korean, english, math)
                    }

                    override fun soccer() {
                        printAction(Activity.TWO, name)
                    }

                    override fun swimming() {
                        printAction(Activity.FOUR, name)
                    }

                }
            }

            // 배구부 학생
            3 -> {
                stu = object : Student(Activity.THREE.action, name, grade, korean, english, math), Soccer, Volleyball{
                    override fun study() {
                        printAction(Activity.ONE, name)
                    }

                    override fun printInfo() {
                        println("소속 : $team")
                        println("이름 : $name")
                        println("학년 : ${grade}학년")
                        println("국어 : ${korean}점")
                        println("영어 : ${english}점")
                        println("수학 : ${math}점")
                        printTotalAndAvg(korean, english, math)
                    }

                    override fun soccer() {
                        printAction(Activity.TWO, name)
                    }

                    override fun volleyBall() {
                        printAction(Activity.THREE, name)
                    }

                }
            }

            // 수영부 학생
            4 -> {
                stu = object : Student(Activity.FOUR.action, name, grade, korean, english, math), Swimming, Volleyball{
                    override fun study() {
                        printAction(Activity.ONE, name)
                    }

                    override fun printInfo() {
                        println("소속 : $team")
                        println("이름 : $name")
                        println("학년 : ${grade}학년")
                        println("국어 : ${korean}점")
                        println("영어 : ${english}점")
                        println("수학 : ${math}점")
                        printTotalAndAvg(korean, english, math)
                    }

                    override fun volleyBall() {
                        printAction(Activity.THREE, name)
                    }

                    override fun swimming() {
                        printAction(Activity.FOUR, name)
                    }

                }
            }
        }

        return stu
    }

    // 학생 정보 출력하는 함수
    fun studentAction(){
        val classRoom = ClassRoom()
        val playground = Playground()
        val pool = Pool()
        val gym = Gym()

        // 학생 정보 출력
        for (stu in studentList){
            stu.printInfo()
        }
        var general = classRoom.study(studentList)
        println()
        var soccer = playground.soccer(studentList)
        println()
        var volley = gym.volleyBall(studentList)
        println()
        var swim = pool.swim(studentList)
        println()

        printStudentTotal(studentList)
    }

    // 개인 총점 및 평균 출력 함수
    fun printTotalAndAvg(korean: Int, english: Int, math: Int) {
        val total = korean + english + math
        val avg = total / 3

        println("개인 총점 : ${total}점")
        println("개인 평균 : ${avg}점")
        println("----------------------")
    }

    // 열거형을 입력받아 출력하는 함수
    fun printAction(act: Activity, name: String) {
        when (act.action) {
            "일반" -> println("$name 학생이 교실에서 공부하고 있습니다.")
            "축구부" -> println("$name 학생이 운동장에서 축구를 하고 있습니다.")
            "수영부" -> println("$name 학생이 수영장에서 수영을 하고 있습니다.")
            "배구부" -> println("$name 학생이 체육관에서 배구를 하고 있습니다.")
        }
    }

    // 전체 학생 관련 자료 출력 함수
    fun printStudentTotal(stuList: ArrayList<Student>){
        var general = 0
        var soccer = 0
        var volley = 0
        var swim = 0

        for (stu in stuList){
            if (stu.team == "일반"){
                general++
            }
            else if(stu.team == "축구부"){
                soccer++
            }
            else if(stu.team == "배구부"){
                volley++
            }
            else {
                swim++
            }
        }

        println("전체 학생 수 : ${stuList.size}명")
        println("일반 학생 수  : ${general}명")
        println("축구부 학생 수 : ${soccer}명")
        println("배구부 학생 수 : ${volley}명")
        println("수영부 학생 수 :${swim}명")
        println("축구를 하는 학생 수 : ${soccer + volley}명")
        println("배구를 하는 학생 수 : ${volley + swim}명")
        println("수영을 하는 학생 수 : ${swim + soccer}명")
    }
}

// 교실
class ClassRoom {
    fun study(studentList: ArrayList<Student>) {
        for (stu in studentList) {
            stu.study()
        }
    }
}

// 운동장
class Playground {
    fun soccer(studentList: ArrayList<Student>) {

        for (stu in studentList) {
            if (stu is Soccer) {
                stu.soccer()
            }
        }
    }
}

// 체육관
class Gym {
    fun volleyBall(studentList: ArrayList<Student>) {
        for (stu in studentList) {
            if (stu is Volleyball) {
                stu.volleyBall()
            }
        }
    }
}

// 수영장
class Pool {
    fun swim(studentList: ArrayList<Student>) {
        for (stu in studentList) {
            if (stu is Swimming) {
                stu.swimming()
            }
        }
    }
}

// 모든 학생
open abstract class Student(
    var team: String,
    var name: String,
    var grade: Int,
    var korean: Int,
    var english: Int,
    var math: Int ) {

    // 공부
    open abstract fun study()

    // 학생 정보 출력
    open abstract fun printInfo()
}

// 축구하는 학생 interface
interface Soccer {
    fun soccer()
}

// 배구하는 학생 interface
interface Volleyball {
    fun volleyBall()
}

// 수영하는 학생 interface
interface Swimming {
    fun swimming()
}