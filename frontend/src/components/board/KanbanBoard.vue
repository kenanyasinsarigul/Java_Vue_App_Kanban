<template>
  <div class="container">
    <update-delete-task
      v-if="selectedTask && project"
      :taskCatch="selectedTask"
      @updated="taskUpdated"
      :project="project"
    />
    <div class="row">
      <div
        v-for="column in columns"
        :key="column.title"
        class="p-3 col rounded"
        style="
          background-color: rgb(244, 245, 247);
          margin-right: 10px;
          min-height: 650px;
        "
      >
        <p class="columnTitle">{{ column.title }}</p>
        <draggable
          :list="column.tasks"
          :animation="200"
          @change="onChange($event, column)"
          ghost-class="ghost-card"
          group="tasks"
          style="height: 100%"
        >
          <div
            v-for="task in column.tasks"
            :key="task.id"
            @click="selectTask(task)"
            v-b-modal.updateDeleteTask
          >
            <task-card :task="task" class="mt-3 cursor-move"></task-card>
          </div>
        </draggable>
      </div>
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";
import TaskCard from "./TaskCard.vue";
import Board from "../../client/Board.js";
import UpdateDeleteTask from "../../views/task/UpdateDeleteTask.vue";

export default {
  name: "App",
  components: {
    TaskCard,
    draggable,
    UpdateDeleteTask,
  },
  props: ["project"],
  methods: {
    taskUpdated() {
      this.loadTasks();
    },
    loadTasks: function () {
      setInterval(() => {
        if (this.project) {
          Board.getTasks(this.project.id).then((res) => {
            this.columns.map((e) => {
              e.tasks = res.data.filter((task) => e.id == task.status);
            });
          });
        }
      }, 1500);
    },
    async onChange(e, column) {
      if (e.added) {
        const task = e.added.element;
        task.status = column.id;
        Board.update(task);
      }
    },
    selectTask(task) {
      this.selectedTask = { ...task };
    },
  },
  mounted() {
    this.loadTasks();
    Board.getTasks(this.project.id).then((res) => {
      this.columns.map((e) => {
        e.tasks = res.data.filter((task) => e.id == task.status);
      });
    });
  },
  data() {
    return {
      selectedTask: null,
      columns: [
        {
          title: "Todo",
          id: "TODO",

          tasks: [],
        },
        {
          title: "In Progress",
          id: "IN_PROGRESS",

          tasks: [],
        },
        {
          title: "Review",
          id: "REVIEW",

          tasks: [],
        },
        {
          title: "Test",
          id: "TEST",

          tasks: [],
        },
        {
          title: "Done",
          id: "DONE",

          tasks: [],
        },
      ],
    };
  },
};
</script>

<style scoped>
.ghost-card {
  opacity: 0.5;
  background: #f7fafc;
  border: 1px solid #4299e1;
}
.info {
  text-align: center;
  font-size: 2rem;
  font-family: monospace;
  background-color: #f8f9fa;
}
.columnTitle {
  font-size: 1.2em;
  font-weight: bold;
}
</style>
