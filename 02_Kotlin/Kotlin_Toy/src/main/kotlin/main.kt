import java.util.Scanner

/*장난감 공장

생산할 장난감의 종류를 선택해주세요
1. 로보트 장난감, 2. 레고, 3. BB탄 총, 4. 잠만보인형, 0. 생산끝

잘못된 번호를 입력했을 경우 잘못 입력하였습니다를 출력한다.

모든 입력이 완료되면 다음과 같이 출력한다.

총 : 00개
로보트 장난감 : 00개
레고 : 00개
BB탄 총 : 00개
잠만보인형 : 00개

로보트 장난감
가격 : 5000원
크기 : 로보트만큼 크다

레고
가격 : 50000원
크기 : 레고만큼 크다

BB탄 총
가격 : 10000원
크기 : BB탄 총 만큼 크다

잠만보인형
가격 : 20000원
크기 : 잠만보 만큼 크다

생산된 장난감 총 가격 : 0000000원
생산된 장난감 평균 가격 : 000000원 */

val toyList = ArrayList<Toy>()
var robotTotal = 0
var legoTotal = 0
var BB = 0
var jammanbo = 0

fun main() {
    var factory = Factory()
    factory.inputToyNumber()
    toyList[0].printTotal()
    toyList[0].toyPrice(toyList)
}

class Factory{
    val scan:Scanner = Scanner(System.`in`)

    // 장난감 번호 입력받는 기능
    fun inputToyNumber(){
        var choiceNumber = 0

        while(true){
            println("생산할 장난감의 종류를 선택해주세요.")
            print("1. 로보트 장난감, 2. 레고, 3. BB탄 총, 4. 잠만보인형, 0. 생산끝 : ")
            choiceNumber = scan.nextInt()

            if (choiceNumber !in 1..4){
                if (choiceNumber == 0){
                    break
                }
                println("잘못 입력하였습니다.")
                continue
            }
            getItem(choiceNumber)
        }
    }

    fun getItem(toyNumber:Int) = when(toyNumber){
        1 -> toyList.add(Toy("로보트 장난감", 5000, "로보트 만큼 크다"))
        2 -> toyList.add(Toy("레고", 50000, "레고 만큼 크다"))
        3 -> toyList.add(Toy("BB탄 총", 10000, "BB탄 총 만큼 크다"))
        4 -> toyList.add(Toy("잠만보 인형", 20000, "잠만보 만큼 크다"))
        else -> toyList.add(Toy("아무거나", 1, "아무거나"))
    }
}

class Toy(var name:String, var price:Int, var toySize:String){

    init {
        if (this.name.equals("로보트 장난감")){
            robotTotal++
        } else if(this.name.equals("레고")){
            legoTotal++
        } else if(this.name.equals("BB탄 총")){
            BB++
        } else {
            jammanbo++
        }
    }

    fun printTotal(){
        println("""----------------------
            |총 ${toyList.size}개
            |로보트 장난감 : ${robotTotal}개
            |레고 : ${legoTotal}개
            |BB탄 총 : ${BB}개
            |잠만보 인형 : ${jammanbo}개
            |------------------------
        """.trimMargin())
    }

    fun toyPrice(toyList : ArrayList<Toy>){
        var priceTotal = 0
        for (toy in toyList){
            priceTotal += toy.price
        }
        println("생산된 장난감 총 가격 : ${priceTotal}원")
        println("생산된 장난감 평균 가격 : ${priceTotal/toyList.size}원")
    }
}
