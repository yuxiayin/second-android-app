package com.example.second_android_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.second_android_app.ui.theme.Second_android_appTheme

class LinearLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)
    }
}

class TableLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_layout)
    }
}

class CalculatorConstraintActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_constraint)
    }
}

class SpaceConstraintActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_constraint)
    }
}

private data class StudyTask(val id: Int, val name: String, val completed: Boolean)

class ComposeTasksActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Second_android_appTheme { StudyTaskScreen() } }
    }
}

@Composable
private fun StudyTaskScreen() {
    val tasks = remember {
        mutableStateListOf(
            StudyTask(1, "学习 Column 和 Row", true),
            StudyTask(2, "学习状态管理", false),
            StudyTask(3, "完成 Compose 实验", false)
        )
    }
    var input by remember { mutableStateOf("") }
    var nextId by remember { mutableStateOf(4) }
    val completed = tasks.count { it.completed }

    Scaffold { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding).padding(20.dp)) {
            Text(
                "课程学习任务",
                color = Color(0xFFC62828),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    placeholder = { Text("请输入学习任务") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        val name = input.trim()
                        if (name.isNotEmpty()) {
                            tasks.add(StudyTask(nextId++, name, false))
                            input = ""
                        }
                    },
                    modifier = Modifier.padding(start = 10.dp)
                ) { Text("添加") }
            }
            Spacer(Modifier.height(18.dp))
            Text("已完成：$completed / ${tasks.size}", color = Color.Gray)
            Spacer(Modifier.height(8.dp))
            LazyColumn {
                items(tasks, key = { it.id }) { task ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.completed,
                            onCheckedChange = { checked ->
                                val index = tasks.indexOfFirst { it.id == task.id }
                                if (index >= 0) tasks[index] = task.copy(completed = checked)
                            }
                        )
                        Text(
                            task.name,
                            modifier = Modifier.weight(1f),
                            style = TextStyle(
                                textDecoration = if (task.completed) TextDecoration.LineThrough else TextDecoration.None
                            )
                        )
                        TextButton(onClick = { tasks.remove(task) }) {
                            Text("删除", color = Color(0xFFC62828))
                        }
                    }
                    HorizontalDivider(color = Color(0xFFE0E0E0))
                }
            }
        }
    }
}
