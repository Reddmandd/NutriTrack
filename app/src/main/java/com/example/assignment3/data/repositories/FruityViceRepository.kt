import com.example.assignment3.data.dao.FruityViceAPI
import com.example.assignment3.data.model.Fruit
import com.example.assignment3.data.model.Nutrition


class FruityViceRepository() {
    private val api = FruityViceAPI.create()

    suspend fun searchFruit(fruitName: String): Fruit {

        try {
            return api.getFruitByName(fruitName)
        } catch (e: Exception) {
            return Fruit("-", "-", "-", "-", Nutrition(0f, 0f, 0f, 0f, 0f))
        }
    }
}