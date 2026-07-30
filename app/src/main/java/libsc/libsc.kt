package libsc

import android.util.Log

const val TAG = "libsc"

fun encrypt(text: String, shift: Int = 0): String {
    Log.d(TAG, "Encrypting text (shift = $shift): $text")

    // Grab needed data from the DB
    val keys = db.keys.toList()
    val values = db.values.toList()
    val size = values.size

    // Handle shift wrapping
    val eShift = Math.floorMod(shift, size)

    // Shift the DB
    val shiftedValues = values.subList(eShift, size) + values.subList(0, eShift)

    // Rebuild the temporary shifted directory
    val sDB: Map<Char, Char> = keys.zip(shiftedValues).toMap()

    // Match Python reference by dropping unmatched chars
    val ciphertext = text.lowercase().map { char ->
        sDB[char] ?: ""
    }.joinToString("")

    Log.d(TAG, "Ciphertext: $ciphertext")
    return ciphertext
}

fun decrypt(ciphertext: String, shift: Int = 0): String {
    Log.d(TAG, "Decrypting ciphertext: $ciphertext")

    // Grab needed data from the DB
    val keys = db.keys.toList()
    val values = db.values.toList()
    val size = values.size

    // Handle shift wrapping
    val eShift = Math.floorMod(shift, size)

    // Shift the DB
    val shiftedValues = values.subList(eShift, size) + values.subList(0, eShift)

    // Build the inverse shifted db
    val sDB: Map<Char, Char> = shiftedValues.zip(keys).toMap()

    // Match Python reference by dropping unmatched chars
    val text = ciphertext.map { char ->
        sDB[char] ?: ""
    }.joinToString("")

    Log.d(TAG, "Decrypted ciphertext: $text")
    return text
}