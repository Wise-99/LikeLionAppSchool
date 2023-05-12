import java.util.*

/*자판기를 구현한다.

음료수를 고르세요
1. 콜라(1000원), 2. 사이다(1000원), 3.커피(1500원), 4.복숭아아이스트(2000원) : 1

만약 그외의 번호를 입력하면.... 다시 입력해주세요 라고 출력하고 위의 메뉴가 다시 나오게 한다.

현재 0원/부족 1000원
동전을 넣어주세요 : 500
현재 500원/부족 500원
동전을 넣어주세요 : 1000
현재 1500원/부족 0원

콜라가 나왔습니다.
잔액은 500원 입니다.

콜라는 1000원이고
양은 300ml 입니다
회사는 코카콜라 입니다.

사이다는 1000원이고
양은 300ml 입니다
회사는 칠성입니다.

커피는 1500원이고
양은 500ml 입니다
회사는 별다방입니다.

복숭아아스티는 2000원이고
양은 700ml 입니다
회사는 빽다방입니다*/

val scan : Scanner = Scanner(System.`in`)

fun main() {
    var number = drinkNumber()
    var drink : Drink

    if (number == 1){
        drink = Drink("콜라",1000, 300, "코카콜라")
    } else if(number == 2){
        drink = Drink("사이다",1000,300,"칠성")
    } else if(number == 3){
        drink = Drink("커피",1500,500,"별다방")
    } else {
        drink = Drink("복숭아 아이스티",2000,700,"빽다방")
    }
    val money = drink.moneyCheck()
    drink.printBalance(money)
    drink.printInfo()
}

fun drinkNumber() : Int {
    var drink : Int
    while(true){
        print("""음료수를 고르세요
            |1. 콜라(1000원), 2. 사이다(1000원), 3.커피(1500원), 4.복숭아아이스트(2000원) :""".trimMargin())
        drink = scan.nextInt()
        if (drink !in 1..4){
            println("다시 입력해주세요")
            continue
        }
        else {
            break
        }
    }
    return drink
}

class Drink (var name:String, var price:Int, var liter:Int, var company:String){

    fun moneyCheck():Int {
        var total = 0

        while(true){
            print("동전을 넣어주세요 :")
            var money = scan.nextInt()
            total += money
            if (total < price) {
                println("현재 ${total}원/부족 ${price - total}원")
            } else {
                println("현재 ${total}원/부족 0원")
                break
            }
        }
        return total - price
    }

    fun printBalance(money:Int){
        println("""----------------
            |${name}가 나왔습니다.
            |잔액은 ${money}원 입니다.
            |------------------
        """.trimMargin())
    }

    fun printInfo(){
        println("""--------------------
            |${name}는 ${price}원이고
            |양은 ${liter}ml 입니다.
            |회사는 ${company} 입니다.
            |-----------------------
        """.trimMargin())
    }
}