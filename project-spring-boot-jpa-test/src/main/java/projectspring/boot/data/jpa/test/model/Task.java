package projectspring.boot.data.jpa.test.model;


@Entity
@Table(name = "task")

public class Task {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private boolean status;

    public Task() {

    }

    public Task(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setstatus(boolean isStatus) {
        this.status = isStatus;
    }

    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", desc=" + description + ", status=" + status + "]";
    }
}