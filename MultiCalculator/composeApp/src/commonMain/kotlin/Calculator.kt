class Calculator {
    fun add(left: Int, right: Int): Int{
                var addition = left + right
                return addition
    }
    fun subtract(left: Int, right: Int): Int {
        var subtraction = left - right
        return subtraction
    }
    fun multiply(left: Int, right: Int): Int {
        var multiplication = left * right
        return multiplication
    }
    fun divide(left: Int, right: Int): Int {
        if(right == 0){
            throw IllegalArgumentException("Cannot divide by zero")
        }
        var division = left / right
        return division
    }
}