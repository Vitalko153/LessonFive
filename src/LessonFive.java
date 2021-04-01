
public class LessonFive {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String args[]) {
        float[] array = new float[SIZE];
        arrayToOne(array);
        firstThread(array);

        arrayToOne(array);
        secondThread(array);
    }

    public static void arrayToOne(float[] array){
        for (int i = 0; i < SIZE; i++) {
            array[i] = 1;
        }
    }

        public static void firstThread(float[] array){
            long startFirst = System.currentTimeMillis();
            for (int i = 0; i < SIZE; i++) {
                array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.out.println("Первый медод: " + (System.currentTimeMillis() - startFirst) + " мс.");
        }

        public static void secondThread (float[] array) {
            float[] arrayOnePart = new float[HALF];
            float[] arrayTwoPart = new float[HALF];

            long startSecond = System.currentTimeMillis();

            System.arraycopy(array, 0, arrayOnePart, 0, HALF);
            System.arraycopy(array, HALF, arrayTwoPart, 0, HALF);

            Thread t1 = new Thread(() -> {
                for (int i = 0; i < HALF; i++) {
                    arrayOnePart[i] = (float) (arrayOnePart[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            });

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < HALF; i++) {
                    arrayTwoPart[i] = (float) (arrayTwoPart[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
                }
            });

            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.arraycopy(arrayOnePart, 0, array, 0, HALF);
            System.arraycopy(arrayTwoPart, 0, array, HALF, HALF);

            System.out.println("Второй метод: " + (System.currentTimeMillis() - startSecond) + " мс.");
        }
        
    }
