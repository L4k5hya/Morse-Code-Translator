import javax.sound.sampled.*;
import java.util.HashMap;

//handel our logic
public class MorseCodeController {

    //HashMap of character type
    private HashMap<Character , String> morseCodeMap;

    public MorseCodeController(){
        morseCodeMap = new HashMap<>();

        morseCodeMap.put('A', ".-");
        morseCodeMap.put('a', ".-");
        morseCodeMap.put('B', "-...");
        morseCodeMap.put('b', "-...");
        morseCodeMap.put('C', "-.-.");
        morseCodeMap.put('c', "-.-.");
        morseCodeMap.put('D', "-..");
        morseCodeMap.put('d', "-..");
        morseCodeMap.put('E', ".");
        morseCodeMap.put('e', ".");
        morseCodeMap.put('F', "..-.");
        morseCodeMap.put('f', "..-.");
        morseCodeMap.put('G', "--.");
        morseCodeMap.put('g', "--.");
        morseCodeMap.put('H', "....");
        morseCodeMap.put('h', "....");
        morseCodeMap.put('I', "..");
        morseCodeMap.put('i', "..");
        morseCodeMap.put('J', ".---");
        morseCodeMap.put('j', ".---");
        morseCodeMap.put('K', "-.-");
        morseCodeMap.put('k', "-.-");
        morseCodeMap.put('L', ".-..");
        morseCodeMap.put('l', ".-..");
        morseCodeMap.put('M', "--");
        morseCodeMap.put('m', "--");
        morseCodeMap.put('N', "-.");
        morseCodeMap.put('n', "-.");
        morseCodeMap.put('O', "---");
        morseCodeMap.put('o', "---");
        morseCodeMap.put('P', ".--.");
        morseCodeMap.put('p', ".--.");
        morseCodeMap.put('Q', "--.-");
        morseCodeMap.put('q', "--.-");
        morseCodeMap.put('R', ".-.");
        morseCodeMap.put('r', ".-.");
        morseCodeMap.put('S', "...");
        morseCodeMap.put('s', "...");
        morseCodeMap.put('T', "-");
        morseCodeMap.put('t', "-");
        morseCodeMap.put('U', "..-");
        morseCodeMap.put('u', "..-");
        morseCodeMap.put('V', "...-");
        morseCodeMap.put('v', "...-");
        morseCodeMap.put('W', ".--");
        morseCodeMap.put('w', ".--");
        morseCodeMap.put('X', "-..-");
        morseCodeMap.put('x', "-..-");
        morseCodeMap.put('Y', "-.--");
        morseCodeMap.put('y', "-.--");
        morseCodeMap.put('Z', "--..");
        morseCodeMap.put('z', "--..");
    }

    public String translateToMorse(String textToTranslate) {
        StringBuilder translatedText = new StringBuilder();
        for (Character letter : textToTranslate.toCharArray()){
            translatedText.append(morseCodeMap.get(letter) + " ");
        }
        return translatedText.toString();
    }

    public void playSound(String[] morseMessage) throws InterruptedException, LineUnavailableException {

        AudioFormat audioFormat = new AudioFormat(44100 , 16 , 1 , true , false);

        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
        SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();


        int dotDuration = 200;
        int dashDuration = (int) (1.5 * dotDuration);
        int slashDuration = 2 * dashDuration;


        for (String pattern : morseMessage){

            for (char c : pattern.toCharArray()) {
                if (c == '.') {
                    playBeep(sourceDataLine, dotDuration);
                    Thread.sleep(dotDuration);
                }else if (c == '-') {
                    playBeep(sourceDataLine, dashDuration);
                    Thread.sleep(dotDuration);
                } else if (c == '/') {
                    Thread.sleep(slashDuration);
                }
            }

            Thread.sleep(dotDuration);
        }
        sourceDataLine.drain();
        sourceDataLine.stop();
        sourceDataLine.close();
    }

    private void playBeep(SourceDataLine line , int duration) {

        byte[] data = new byte[duration * 44100 / 1000];

        for (int i = 0 ; i < data.length; i++) {
            double angle = i / (44100.0/440) * 2 * Math.PI;



            data[i] = (byte) (Math.sin(angle) * 127);
        }

        line.write(data , 0  ,data.length);
    }
}
