package tmg.flashback.persistence.flashback.dao

import androidx.room3.*
import kotlinx.coroutines.flow.Flow
import tmg.flashback.persistence.flashback.models.circuit.Circuit
import tmg.flashback.persistence.flashback.models.circuit.CircuitHistory
import tmg.flashback.persistence.flashback.models.circuit.CircuitRound
import tmg.flashback.persistence.flashback.models.circuit.CircuitRoundResult
import tmg.flashback.persistence.flashback.models.overview.Schedule

@Dao
interface CircuitDao {

    @Query("SELECT * FROM circuit WHERE id == :id LIMIT 1")
    fun getCircuit(id: String): Flow<Circuit?>

    @Transaction
    @Query("SELECT * FROM circuit WHERE id == :id")
    fun getCircuitHistory(id: String): Flow<CircuitHistory>

    @Query("SELECT COUNT(*) FROM CircuitRound WHERE circuit_id == :id")
    suspend fun getCircuitRounds(id: String): Int

    @Query("SELECT * FROM Circuit")
    fun getCircuits(): Flow<List<Circuit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCircuit(circuit: List<Circuit>)


    @Transaction
    suspend fun replaceAllForCircuit(
        circuitId: String,
        rounds: List<CircuitRound>
    ) {
        deleteRoundsForCircuit(circuitId)
        insertCircuitRounds(rounds)
    }

    @Query("DELETE FROM CircuitRound WHERE circuit_id == :circuitId")
    suspend fun deleteRoundsForCircuit(circuitId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCircuitRounds(rounds: List<CircuitRound>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCircuitRoundResults(results: List<CircuitRoundResult>)
}