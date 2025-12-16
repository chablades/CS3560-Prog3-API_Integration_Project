package com.example.AIWorldBuilder.core.service;

import com.example.AIWorldBuilder.core.ai.AIRequest;
import com.example.AIWorldBuilder.core.ai.AIStreamListener;
import com.example.AIWorldBuilder.core.ai.GeminiClient;
import com.example.AIWorldBuilder.core.ai.InvalidApiKeyException;

public class AIService {

    private final GeminiClient geminiClient; 
    private Thread currentThread;

    public AIService() {
        this.geminiClient = GeminiClient.getInstance();
    }

    // Send a request to the AI provider (or simulate in test mode)
    public void send(AIRequest request, AIStreamListener listener) {


        if (request.getPrompt() == null || request.getPrompt().isBlank()) {
            listener.onError(new IllegalArgumentException("Prompt cannot be empty"));
            return;
        }

        if (request.getApiKey() == null || request.getApiKey().isBlank()) {
            listener.onError(new InvalidApiKeyException("No API key configured"));
            return;
        }

        if (request.isTestMode()) {
            testModeStream(listener);
            return;
        }
        else {
            currentThread = new Thread(() -> geminiClient.sendRequest(request, listener));
            currentThread.start();
        }
    }

    // Simulate streaming in test mode
    private void testModeStream(AIStreamListener listener) {
        String simulatedResponse = "Mississippi is a state in the Southeastern and Deep South regions" 
        + "of the United States. It borders Tennessee to the north, Alabama to the east, the Gulf of Mexico to the south,"
        + "Louisiana to the southwest, and Arkansas to the northwest. Mississippi's western boundary is largely defined by"
        + " the Mississippi River, or its historical course.[7] Mississippi is the 32nd largest by area and 35th-most populous of the 50 U.S."
        + "states and has the lowest per-capita income. Jackson is both the state's capital and largest city. Greater Jackson is the state's most"
        + "populous metropolitan area, with a population of 591,978 in 2020. Other major cities include Gulfport, Southaven, Hattiesburg, Biloxi,"
        + "Olive Branch, Tupelo, Meridian, and Greenville.[8] The state's history traces back to around 9500 BC with the arrival of Paleo-Indians, "
        + "evolving through periods marked by the development of agricultural societies, rise of the Mound Builders, and flourishing of the Mississippian culture. European exploration began with the Spanish in the 16th century, followed by French colonization in the 17th century. Mississippi's strategic location along the Mississippi River made it a site of significant economic and strategic importance, especially during the era of cotton plantation agriculture, which led to its wealth pre-Civil War, but entrenched slavery and racial segregation. On December 10, 1817, Mississippi became the 20th state admitted to the Union. By 1860, Mississippi was the nation's top cotton-producing state and slaves accounted for 55% of the state population.[9] Mississippi declared its secession from the Union on January 9, 1861, and was one of the seven original Confederate States, which constituted the largest slaveholding states in the nation. Following the Civil War, it was restored to the Union on February 23, 1870.[10] Mississippi's political and social landscape was dramatically shaped by the Civil War, Reconstruction era, and civil rights movement, with the state playing a pivotal role in the struggle for civil rights. From the Reconstruction era to the 1960s, Mississippi was dominated by socially conservative and segregationist Southern Democrats dedicated to upholding white supremacy. Despite progress, Mississippi continues to grapple with challenges related to health, education, and economic development, often ranking among the lowest in the United States in national metrics for wealth, healthcare quality, and educational attainment.[11][12][13][14] Economically, it relies on agriculture, manufacturing, and an increasing focus on tourism, highlighted by its casinos and historical sites. Mississippi produces more than half of the country's farm-raised catfish, and is a top producer of sweet potatoes, cotton and pulpwood. Others include advanced manufacturing, utilities, transportation, and health services.[15] Mississippi is almost entirely within the east Gulf Coastal Plain, and generally consists of lowland plains and low hills. The northwest remainder of the state consists of the Mississippi Delta. Mississippi's highest point is Woodall Mountain at 807 feet (246 m) above sea level adjacent to the Cumberland Plateau; the lowest is the Gulf of Mexico. Mississippi has a humid subtropical climate classification. Mississippi is known for its deep religious roots, which play a central role in its residents' lives. The state ranks among the highest of U.S. states in religiosity. Mississippi is also known for being the state with the highest proportion of African-American residents. The state's governance structure is based on the traditional separation of powers, with political trends showing a strong alignment with conservative values. Mississippi boasts a rich cultural heritage, especially in music, being the birthplace of the blues and contributing significantly to the development of the music of the United States as a whole.";
        currentThread = new Thread(() -> {
            for (char c : simulatedResponse.toCharArray()) {
                listener.onToken(String.valueOf(c));
                try {
                    Thread.sleep(5); // Simulate delay (5ms)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    listener.onCancelled(); 
                    return;
                }
            }
            listener.onComplete();
        });
        currentThread.start();
    }

    // Cancel ongoing request
    public void cancel() {
        if (currentThread != null && currentThread.isAlive()) {
            currentThread.interrupt();
        }
    }
}