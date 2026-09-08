import android.content.Context
import android.os.Message
import androidx.lifecycle.LiveData
import com.example.assignment3.data.model.FoodIntake
import com.example.assignment3.data.model.MotivationalMessage
import com.example.assignment3.data.model.Patient
import com.example.assignment3.data.model.UsersDatabase

class MessageRepository(private val context: Context) {

    private val messageDao = UsersDatabase.Companion.getDatabase(context).messageDao()

    suspend fun insertMessages(message: MotivationalMessage) {
        messageDao.insertMessages(message)
    }

    fun getMessagesByPatientId(patientId: Int): LiveData<List<MotivationalMessage>> {
        return messageDao.getAllMessages(patientId)
    }
}