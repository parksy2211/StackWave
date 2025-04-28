let todoData = JSON.parse(localStorage.getItem('todoData')) || [];

const todoForm = document.getElementById('todo-form');
const todoInput = document.getElementById('todo-input');
const todoList = document.getElementById('todo-list');
const deleteAllBtn = document.getElementById('delete-all-btn');

// 렌더링 함수
function renderTodo() {
  todoList.innerHTML = '';

  todoData.forEach(item => {
    const li = document.createElement('li');
    li.className = 'todo-item';
    if (item.completed) {
      li.classList.add('completed');
    }
    li.dataset.id = item.id;

    const leftDiv = document.createElement('div');
    leftDiv.className = 'todo-left';

    const checkbox = document.createElement('input');
    checkbox.type = 'checkbox';
    checkbox.checked = item.completed;
    checkbox.addEventListener('change', () => toggleComplete(item.id));

    const span = document.createElement('span');
    span.textContent = item.title;

    leftDiv.appendChild(checkbox);
    leftDiv.appendChild(span);

    const rightDiv = document.createElement('div');
    rightDiv.className = 'todo-right';

    const editBtn = document.createElement('button');
    editBtn.textContent = '편집';
    editBtn.className = 'edit-btn';
    editBtn.addEventListener('click', () => startEdit(item.id, span));

    const deleteBtn = document.createElement('button');
    deleteBtn.textContent = 'x';
    deleteBtn.className = 'delete-btn';
    deleteBtn.addEventListener('click', () => deleteTodo(item.id));

    rightDiv.appendChild(editBtn);
    rightDiv.appendChild(deleteBtn);

    li.appendChild(leftDiv);
    li.appendChild(rightDiv);

    todoList.appendChild(li);
  });
}


// 할 일 추가
todoForm.addEventListener('submit', function(e) {
  e.preventDefault();

  const newTodo = {
    id: Date.now(),
    title: todoInput.value,
    completed: false,
  };

  todoData.push(newTodo);
  saveAndRender();
  todoInput.value = '';
});

// 완료 체크
function toggleComplete(id) {
  todoData = todoData.map(item => {
    if (item.id === id) {
      item.completed = !item.completed;
    }
    return item;
  });
  saveAndRender();
}

// 삭제
function deleteTodo(id) {
  todoData = todoData.filter(item => item.id !== id);
  saveAndRender();
}

// 전체 삭제
deleteAllBtn.addEventListener('click', function() {
  todoData = [];
  saveAndRender();
});

// 수정 시작
function startEdit(id, spanElement) {
  const input = document.createElement('input');
  input.type = 'text';
  input.value = spanElement.textContent;
  input.className = 'edit-input';

  const saveBtn = document.createElement('button');
  saveBtn.textContent = '저장';
  saveBtn.className = 'save-btn';

  const parentLi = spanElement.parentElement.parentElement; 

  const todoLeft = parentLi.querySelector('.todo-left');
  const todoRight = parentLi.querySelector('.todo-right');

  if (todoLeft && todoRight) {
    todoLeft.innerHTML = '';
    todoLeft.appendChild(input);

    todoRight.innerHTML = '';
    todoRight.appendChild(saveBtn);

    saveBtn.addEventListener('click', function() {
      const newTitle = input.value;
      todoData = todoData.map(item => {
        if (item.id === id) {
          item.title = newTitle;
        }
        return item;
      });
      saveAndRender();
    });
  }
}

// 저장하고 다시 그리기
function saveAndRender() {
  localStorage.setItem('todoData', JSON.stringify(todoData));
  renderTodo();
}

// 초기 렌더링
renderTodo();
