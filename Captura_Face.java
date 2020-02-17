/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reconhecimento;

import java.awt.event.KeyEvent;
import java.util.Scanner;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 *
 * @author Azazel
 */
public class Captura_Face {
   
    /*
   public void face() throws FrameGrabber.Exception, InterruptedException{
        Scanner cadastro = new Scanner(System.in);
        int numeroDeAmostras = 25;
        int amostra = 1;
        int idPessoa;
        KeyEvent tecla = null;

        OpenCVFrameConverter.ToMat converteMat = new OpenCVFrameConverter.ToMat();
        OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
        camera.start();

        CascadeClassifier detectorFace = new CascadeClassifier("src\\Recursos\\haarcascade-frontalface-alt.xml");

        CanvasFrame cFrame = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma() / camera.getGamma());
        Frame frameCapturado = null;
        Mat imagemColorida = new Mat();

        System.out.println("Digite o seu ID: ");
        idPessoa = cadastro.nextInt();
        System.out.println("Aperte a tecla 'X' para tirar as fotos: ");
        

        while ((frameCapturado = camera.grab()) != null) {
            imagemColorida = converteMat.convert(frameCapturado);
            Mat imagemCinza = new Mat();
            cvtColor(imagemColorida, imagemCinza, COLOR_BGRA2GRAY);
            RectVector facesDetectadas = new RectVector();
            detectorFace.detectMultiScale(imagemCinza, facesDetectadas, 1.1, 1, 0, new Size(150, 150), new Size(500, 500));

            if (tecla == null) {
                tecla = cFrame.waitKey(1);
            }

            for (int i = 0; i < facesDetectadas.size(); i++) {
                Rect dadosFace = facesDetectadas.get(0);
                rectangle(imagemColorida, dadosFace, new Scalar(0, 0, 255, 0));
                Mat faceCapturada = new Mat(imagemCinza, dadosFace);
                resize(faceCapturada, faceCapturada, new Size(160, 160));

                if (tecla == null) {
                    tecla = cFrame.waitKey(5);
                }

                if (tecla != null) {
                    if (tecla.getKeyChar() == 'x') {
                        if (amostra <= numeroDeAmostras) {
                            imwrite("src\\Fotos\\pessoa." + idPessoa + "." + amostra + ".jpg", faceCapturada);
                            System.out.println("Foto " + amostra + " capturada\n");
                            amostra++;
                        }
                    }

                    tecla = null;

                }
            }

            if (tecla == null) {
                tecla = cFrame.waitKey(20);
            }

            if (cFrame.isVisible()) {
                cFrame.showImage(frameCapturado);
            }

            if (amostra > numeroDeAmostras) {
                break;
            }
        }
        cFrame.dispose();
        camera.stop();
    } 

    */
    
  public static void main(String[] args) throws FrameGrabber.Exception, InterruptedException {
        Scanner cadastro = new Scanner(System.in);
        int numeroDeAmostras = 25;
        int amostra = 1;
        int idPessoa;
        KeyEvent tecla = null;

        OpenCVFrameConverter.ToMat converteMat = new OpenCVFrameConverter.ToMat();
        OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
        camera.start();

        CascadeClassifier detectorFace = new CascadeClassifier("src\\Recursos\\haarcascade-frontalface-alt.xml");

        CanvasFrame cFrame = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma() / camera.getGamma());
        Frame frameCapturado = null;
        Mat imagemColorida = new Mat();

        System.out.println("Digite o seu ID: ");
        idPessoa = cadastro.nextInt();
        System.out.println("Aperte a tecla 'X' para tirar as fotos: ");
        

        while ((frameCapturado = camera.grab()) != null) {
            imagemColorida = converteMat.convert(frameCapturado);
            Mat imagemCinza = new Mat();
            cvtColor(imagemColorida, imagemCinza, COLOR_BGRA2GRAY);
            RectVector facesDetectadas = new RectVector();
            detectorFace.detectMultiScale(imagemCinza, facesDetectadas, 1.1, 1, 0, new Size(150, 150), new Size(500, 500));

            if (tecla == null) {
                tecla = cFrame.waitKey(1);
            }

            for (int i = 0; i < facesDetectadas.size(); i++) {
                Rect dadosFace = facesDetectadas.get(0);
                rectangle(imagemColorida, dadosFace, new Scalar(0, 0, 255, 0));
                Mat faceCapturada = new Mat(imagemCinza, dadosFace);
                resize(faceCapturada, faceCapturada, new Size(160, 160));

                if (tecla == null) {
                    tecla = cFrame.waitKey(5);
                }

                if (tecla != null) {
                    if (tecla.getKeyChar() == 'x') {
                        if (amostra <= numeroDeAmostras) {
                            imwrite("src\\Fotos\\pessoa." + idPessoa + "." + amostra + ".jpg", faceCapturada);
                            System.out.println("Foto " + amostra + " capturada\n");
                            amostra++;
                        }
                    }

                    tecla = null;

                }
            }

            if (tecla == null) {
                tecla = cFrame.waitKey(20);
            }

            if (cFrame.isVisible()) {
                cFrame.showImage(frameCapturado);
            }

            if (amostra > numeroDeAmostras) {
                break;
            }
        }
        cFrame.dispose();
        camera.stop();
    }

}
