package thread;

public class Syncronizer {
    private final int numAgents;
    private int agentsFinishedPhase = 0;
    private boolean initBackDone = false;
    private boolean modelFinishedBackProp = false;
    private boolean optimizationDone = true;

    public Syncronizer(int numAgents) {
        this.numAgents = numAgents;
    }

    /**
     *  FASE 1: Caricamento Array (Agenti)
     * @throws InterruptedException 
     */
    public synchronized void startingSendActions() throws InterruptedException {
        // Gli agenti aspettano che l'optimization precedente sia finita
        while (!optimizationDone){
            try { 
            	wait(); 
            } 
            catch (InterruptedException e){
            	throw new InterruptedException(e.getMessage());
            }
        }
    }

    public synchronized void terminatingSendActions() throws InterruptedException {
        agentsFinishedPhase++;
        if (agentsFinishedPhase == numAgents) {
            optimizationDone = false; // Inizia un nuovo ciclo
            notifyAll(); // Sveglia il Model per l'inizializzazione BackProp
        }
        // Aspetta che il Model finisca l'inizializzazione per poter caricare il prossimo array
        while (!initBackDone) {
            try { 
            	wait(); 
            } 
            catch (InterruptedException e){
            	throw new InterruptedException(e.getMessage());
            }
        }
    }

    /**
     *  FASE 2: Model Inizializza BackProp 
     * @throws InterruptedException 
     */
    public synchronized void waitForAgentsForInit() throws InterruptedException {
        while (agentsFinishedPhase < numAgents) {
            try {
            	wait(); 
            } 
            catch (InterruptedException e){
            	throw new InterruptedException(e.getMessage());
            }
        }
    }

    public synchronized void finishInitBack() {
        initBackDone = true;
        agentsFinishedPhase = 0; // Resettiamo il counter per la fase concorrente
        notifyAll(); // Sblocca gli agenti: ora possono caricare il nuovo array
    }

    /**
     *  FASE 3: Sincronizzazione Fine BackProp / Fine Caricamento
     * @throws InterruptedException 
     */
    public synchronized void agentFinishedLoadingNext() throws InterruptedException {
        agentsFinishedPhase++;
        if (agentsFinishedPhase == numAgents) notifyAll();
        // Aspetta che il model finisca l'optimization
        while (!optimizationDone) {
            try { 
            	wait(); 
            }catch (InterruptedException e){
            	throw new InterruptedException(e.getMessage());
            }
        }
    }

    public synchronized void modelFinishedBackProp() {
        modelFinishedBackProp = true;
        notifyAll();
    }

    public synchronized void waitForAllBeforeOptimization() throws InterruptedException {
        // Il model aspetta che gli agenti abbiano caricato l'array successivo
        while (agentsFinishedPhase < numAgents || !modelFinishedBackProp) {
            try { 
            	wait(); 
            } 
            catch (InterruptedException e){
            	throw new InterruptedException(e.getMessage());
            }
        }
    }

    public synchronized void finishOptimization() {
        optimizationDone = true;
        initBackDone = false;
        modelFinishedBackProp = false;
        agentsFinishedPhase = 0;
        notifyAll(); // Fa ripartire gli agenti per il nuovo ciclo
    }
}
