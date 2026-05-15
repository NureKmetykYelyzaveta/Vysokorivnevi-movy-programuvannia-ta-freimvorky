package com.example.pz3

import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.security.MessageDigest

enum class Screen {
    MENU,
    LEVEL_1,
    LEVEL_2,
    LEVEL_3,
    LEVEL_4
}

data class WordTask(
    val word: String,
    val hint: String
)

data class DuplicateFile(
    val name: String,
    val uri: Uri,
    val hash: String
)

data class FolderFile(
    val name: String,
    val uri: Uri
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf(Screen.MENU) }

    when (currentScreen) {
        Screen.MENU -> MainScreen(
            onLevel1Click = { currentScreen = Screen.LEVEL_1 },
            onLevel2Click = { currentScreen = Screen.LEVEL_2 },
            onLevel3Click = { currentScreen = Screen.LEVEL_3 },
            onLevel4Click = { currentScreen = Screen.LEVEL_4 }
        )

        Screen.LEVEL_1 -> Level1Screen(
            onBackClick = { currentScreen = Screen.MENU }
        )

        Screen.LEVEL_2 -> Level2Screen(
            onBackClick = { currentScreen = Screen.MENU }
        )

        Screen.LEVEL_3 -> Level3Screen(
            onBackClick = { currentScreen = Screen.MENU }
        )

        Screen.LEVEL_4 -> Level4Screen(
            onBackClick = { currentScreen = Screen.MENU }
        )
    }
}

@Composable
fun MainScreen(
    onLevel1Click: () -> Unit,
    onLevel2Click: () -> Unit,
    onLevel3Click: () -> Unit,
    onLevel4Click: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F8))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Практичне завдання №3",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Мобільна розробка на Android з Kotlin\nВаріант 5",
            fontSize = 16.sp,
            color = Color(0xFF4B5563),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Button(
                    onClick = onLevel1Click,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Рівень 1 — Добуток чисел",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = onLevel2Click,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Рівень 2 — Вгадай слово",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = onLevel3Click,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Рівень 3 — Підрахунок слів у файлі",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = onLevel4Click,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Рівень 4 — Пошук дублікатів файлів",
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Оберіть завдання для виконання",
            fontSize = 15.sp,
            color = Color(0xFF6B7280),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Level1Screen(
    onBackClick: () -> Unit
) {
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Результат з’явиться тут") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F8))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Рівень 1",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Добуток двох чисел",
            fontSize = 18.sp,
            color = Color(0xFF4B5563),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = firstNumber,
                    onValueChange = { firstNumber = it },
                    label = { Text("Перше число") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = secondNumber,
                    onValueChange = { secondNumber = it },
                    label = { Text("Друге число") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val number1 = firstNumber.toDoubleOrNull()
                        val number2 = secondNumber.toDoubleOrNull()

                        resultText = if (number1 == null || number2 == null) {
                            "Помилка: введіть коректні числа"
                        } else {
                            val product = number1 * number2
                            "Добуток: $product"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Обчислити добуток",
                        fontSize = 16.sp
                    )
                }

                Text(
                    text = resultText,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Повернутися в меню")
        }
    }
}

@Composable
fun Level2Screen(
    onBackClick: () -> Unit
) {
    val words = listOf(
        WordTask("kotlin", "Мова програмування для Android"),
        WordTask("android", "Операційна система для мобільних пристроїв"),
        WordTask("student", "Людина, яка навчається"),
        WordTask("phone", "Мобільний пристрій для дзвінків"),
        WordTask("program", "Набір команд для комп’ютера")
    )

    var currentTask by remember { mutableStateOf(words.random()) }
    var userAnswer by remember { mutableStateOf("") }
    var attemptsLeft by remember { mutableIntStateOf(3) }
    var resultText by remember { mutableStateOf("Введіть слово та натисніть кнопку перевірки") }
    var isGameFinished by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F8))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Рівень 2",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Гра «Вгадай слово»",
            fontSize = 18.sp,
            color = Color(0xFF4B5563),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Підказка: ${currentTask.hint}",
                    fontSize = 17.sp,
                    color = Color(0xFF1F2937),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Кількість літер: ${currentTask.word.length}",
                    fontSize = 16.sp,
                    color = Color(0xFF4B5563),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Залишилось спроб: $attemptsLeft",
                    fontSize = 16.sp,
                    color = Color(0xFF4B5563),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = userAnswer,
                    onValueChange = { userAnswer = it },
                    label = { Text("Введіть слово") },
                    enabled = !isGameFinished,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        val answer = userAnswer.trim().lowercase()

                        if (answer.isEmpty()) {
                            resultText = "Помилка: введіть слово"
                        } else if (answer == currentTask.word.lowercase()) {
                            resultText = "Правильно! Ви вгадали слово: ${currentTask.word}"
                            isGameFinished = true
                        } else {
                            attemptsLeft--

                            if (attemptsLeft > 0) {
                                resultText = "Неправильно. Спробуйте ще раз."
                            } else {
                                resultText = "Спроби закінчилися. Правильне слово: ${currentTask.word}"
                                isGameFinished = true
                            }
                        }
                    },
                    enabled = !isGameFinished,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Перевірити",
                        fontSize = 16.sp
                    )
                }

                OutlinedButton(
                    onClick = {
                        currentTask = words.random()
                        userAnswer = ""
                        attemptsLeft = 3
                        resultText = "Введіть слово та натисніть кнопку перевірки"
                        isGameFinished = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Нова гра")
                }

                Text(
                    text = resultText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Повернутися в меню")
        }
    }
}

@Composable
fun Level3Screen(
    onBackClick: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("Файл не обрано") }
    var fileText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Оберіть текстовий файл для підрахунку слів") }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedFileUri = uri
            selectedFileName = uri.lastPathSegment ?: "Обраний файл"

            try {
                val inputStream = context.contentResolver.openInputStream(uri)
                val text = inputStream?.bufferedReader()?.use { it.readText() } ?: ""

                fileText = text
                resultText = "Файл успішно завантажено. Натисніть кнопку для підрахунку слів."
            } catch (e: Exception) {
                fileText = ""
                resultText = "Помилка читання файлу: ${e.message}"
            }
        } else {
            resultText = "Файл не було обрано"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F8))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Рівень 3",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Підрахунок кількості слів у файлі",
            fontSize = 18.sp,
            color = Color(0xFF4B5563),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Обраний файл:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                Text(
                    text = selectedFileName,
                    fontSize = 15.sp,
                    color = Color(0xFF4B5563),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        filePickerLauncher.launch(arrayOf("text/plain"))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Обрати файл",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = {
                        if (selectedFileUri == null) {
                            resultText = "Помилка: спочатку оберіть файл"
                        } else if (fileText.isBlank()) {
                            resultText = "Файл порожній або не містить тексту"
                        } else {
                            val words = fileText
                                .trim()
                                .split(Regex("\\s+"))
                                .filter { it.isNotBlank() }

                            resultText = "Кількість слів у файлі: ${words.size}"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Порахувати слова",
                        fontSize = 16.sp
                    )
                }

                Text(
                    text = resultText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                if (fileText.isNotBlank()) {
                    Text(
                        text = "Попередній перегляд тексту:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = if (fileText.length > 300) {
                            fileText.take(300) + "..."
                        } else {
                            fileText
                        },
                        fontSize = 14.sp,
                        color = Color(0xFF4B5563),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Повернутися в меню")
        }
    }
}

@Composable
fun Level4Screen(
    onBackClick: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    var selectedFolderUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFolderName by remember { mutableStateOf("Папку не обрано") }
    var resultText by remember { mutableStateOf("Оберіть папку для пошуку однакових файлів") }
    var duplicates by remember { mutableStateOf<List<DuplicateFile>>(emptyList()) }

    val folderPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedFolderUri = uri
            selectedFolderName = uri.lastPathSegment ?: "Обрана папка"
            duplicates = emptyList()
            resultText = "Папку обрано. Натисніть кнопку для пошуку дублікатів."

            val flags = android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION or
                    android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION

            try {
                context.contentResolver.takePersistableUriPermission(uri, flags)
            } catch (_: Exception) {
            }
        } else {
            resultText = "Папку не було обрано"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F8))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Рівень 4",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Пошук однакових файлів у папці",
            fontSize = 18.sp,
            color = Color(0xFF4B5563),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Обрана папка:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                Text(
                    text = selectedFolderName,
                    fontSize = 15.sp,
                    color = Color(0xFF4B5563),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        folderPickerLauncher.launch(null)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Обрати папку",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = {
                        val folderUri = selectedFolderUri

                        if (folderUri == null) {
                            resultText = "Помилка: спочатку оберіть папку"
                            duplicates = emptyList()
                        } else {
                            try {
                                val files = getFilesFromFolder(context, folderUri)
                                val hashMap = mutableMapOf<String, MutableList<DuplicateFile>>()

                                for (file in files) {
                                    val hash = calculateFileHash(context, file.uri)

                                    if (hash.isNotEmpty()) {
                                        val duplicateFile = DuplicateFile(
                                            name = file.name,
                                            uri = file.uri,
                                            hash = hash
                                        )

                                        if (!hashMap.containsKey(hash)) {
                                            hashMap[hash] = mutableListOf()
                                        }

                                        hashMap[hash]?.add(duplicateFile)
                                    }
                                }

                                val foundDuplicates = mutableListOf<DuplicateFile>()

                                for (group in hashMap.values) {
                                    if (group.size > 1) {
                                        foundDuplicates.addAll(group.drop(1))
                                    }
                                }

                                duplicates = foundDuplicates

                                resultText = if (duplicates.isEmpty()) {
                                    "Дублікатів не знайдено"
                                } else {
                                    "Знайдено дублікатів: ${duplicates.size}"
                                }
                            } catch (e: Exception) {
                                resultText = "Помилка пошуку: ${e.message}"
                                duplicates = emptyList()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Знайти дублікати",
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = {
                        if (duplicates.isEmpty()) {
                            resultText = "Немає дублікатів для видалення"
                        } else {
                            var deletedCount = 0

                            for (duplicate in duplicates) {
                                try {
                                    val deleted = DocumentsContract.deleteDocument(
                                        context.contentResolver,
                                        duplicate.uri
                                    )

                                    if (deleted) {
                                        deletedCount++
                                    }
                                } catch (_: Exception) {
                                }
                            }

                            duplicates = emptyList()
                            resultText = "Видалено дублікатів: $deletedCount"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Видалити дублікати",
                        fontSize = 16.sp
                    )
                }

                Text(
                    text = resultText,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                if (duplicates.isNotEmpty()) {
                    Text(
                        text = "Список дублікатів:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F2937),
                        modifier = Modifier.fillMaxWidth()
                    )

                    duplicates.forEachIndexed { index, file ->
                        Text(
                            text = "${index + 1}. ${file.name}",
                            fontSize = 14.sp,
                            color = Color(0xFF4B5563),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Повернутися в меню")
        }
    }
}

fun getFilesFromFolder(
    context: android.content.Context,
    folderUri: Uri
): List<FolderFile> {
    val files = mutableListOf<FolderFile>()

    val treeDocumentId = DocumentsContract.getTreeDocumentId(folderUri)

    val childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
        folderUri,
        treeDocumentId
    )

    val projection = arrayOf(
        DocumentsContract.Document.COLUMN_DOCUMENT_ID,
        DocumentsContract.Document.COLUMN_DISPLAY_NAME,
        DocumentsContract.Document.COLUMN_MIME_TYPE
    )

    val cursor = context.contentResolver.query(
        childrenUri,
        projection,
        null,
        null,
        null
    )

    cursor?.use {
        val idIndex = it.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DOCUMENT_ID)
        val nameIndex = it.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_DISPLAY_NAME)
        val mimeIndex = it.getColumnIndexOrThrow(DocumentsContract.Document.COLUMN_MIME_TYPE)

        while (it.moveToNext()) {
            val documentId = it.getString(idIndex)
            val name = it.getString(nameIndex)
            val mimeType = it.getString(mimeIndex)

            val isDirectory = mimeType == DocumentsContract.Document.MIME_TYPE_DIR

            if (!isDirectory) {
                val fileUri = DocumentsContract.buildDocumentUriUsingTree(
                    folderUri,
                    documentId
                )

                files.add(
                    FolderFile(
                        name = name,
                        uri = fileUri
                    )
                )
            }
        }
    }

    return files
}

fun calculateFileHash(
    context: android.content.Context,
    fileUri: Uri
): String {
    return try {
        val digest = MessageDigest.getInstance("SHA-256")
        val inputStream = context.contentResolver.openInputStream(fileUri)

        inputStream?.use { stream ->
            val buffer = ByteArray(8192)

            while (true) {
                val bytesRead = stream.read(buffer)

                if (bytesRead == -1) {
                    break
                }

                digest.update(buffer, 0, bytesRead)
            }
        }

        digest.digest().joinToString("") { byte ->
            "%02x".format(byte)
        }
    } catch (_: Exception) {
        ""
    }
}