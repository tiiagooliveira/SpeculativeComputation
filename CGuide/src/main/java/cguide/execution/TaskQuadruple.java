package cguide.execution;

/**
 * Created with IntelliJ IDEA.
 * User: tiago
 * Date: 29-08-2013
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
public class TaskQuadruple {
    private String task;
    private String sync;
    private String id;
    private String plan;

    public TaskQuadruple(){

    }
    public TaskQuadruple(String task, String sync, String id,String plan){
        this.task = task;
        this.sync = sync;
        this.id = id;
        this.plan = plan;
    }

    public String getSync() {
        return sync;
    }

    public void setSync(String sync) {
        this.sync = sync;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
    public Boolean equals(TaskQuadruple tp){
        if(tp.getPlan()==null && this.getPlan()==null){
            return ((this.getTask().equals(tp.getTask()) && this.getSync().equals(this.getSync()))&&this.getId().equals(tp.getId()));

        }
        return ((this.getTask().equals(tp.getTask()) && this.getSync().equals(this.getSync()))&&this.getId().equals(tp.getId())&&this.getPlan().equals(tp.getPlan()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Boolean isSync(){
        return(this.getTask().equals(this.getSync()));
    }

    @Override
	public String toString() {
        return "task="+task+"\nid="+id+"\nsync="+sync+"\nplan="+plan;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}
