/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reconhecimento;

import java.util.Scanner;
import org.bytedeco.javacpp.DoublePointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_core;
import static org.bytedeco.javacpp.opencv_core.FONT_HERSHEY_PLAIN;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createEigenFaceRecognizer;
import static org.bytedeco.javacpp.opencv_face.createFisherFaceRecognizer;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGRA2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import org.bytedeco.javacpp.opencv_objdetect;
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
public class Reconhecimento {

    public static void main(String[] args) throws FrameGrabber.Exception {
        Scanner nome_pessoa = new Scanner(System.in);
        String nome_1, nome_2;
        
        System.out.println("Informe o nome da 1º pessoa: ");
        nome_1 = nome_pessoa.next();
        
        System.out.println("Informe o nome da 2º pessoa: ");
        nome_2 = nome_pessoa.next();
        
        String[] pessoas = {"", nome_1, nome_2};
        String nome;
        int predicao;
        int x, y;

        OpenCVFrameConverter.ToMat converteMat = new OpenCVFrameConverter.ToMat();
        OpenCVFrameGrabber camera = new OpenCVFrameGrabber(0);
        camera.start();

        CascadeClassifier detectorFace = new opencv_objdetect.CascadeClassifier("src\\Recursos\\haarcascade-frontalface-alt.xml");
      
      //**** PODE-SE USAR METODO DO EINGENFACE PARA RECONHECER A FACE OU O FISHERFACE  
        
        //FaceRecognizer reconhecedor = createEigenFaceRecognizer();
        //reconhecedor.load("src\\Recursos\\classificadorEigenFaces.yml");
        //reconhecedor.setThreshold(4000);
       
       FaceRecognizer reconhecedor = createFisherFaceRecognizer();
       reconhecedor.load("src\\Recursos\\classificadorFisherFaces.yml");
       
        CanvasFrame cFrame = new CanvasFrame("Preview", CanvasFrame.getDefaultGamma() / camera.getGamma());
        Frame frameCapturado = null;
        Mat imagemColorida = new opencv_core.Mat();

        while ((frameCapturado = camera.grab()) != null) {
            imagemColorida = converteMat.convert(frameCapturado);
            Mat imagemCinza = new Mat();
            cvtColor(imagemColorida, imagemCinza, COLOR_BGRA2GRAY);
            RectVector facesDetectadas = new opencv_core.RectVector();
            detectorFace.detectMultiScale(imagemCinza, facesDetectadas, 1.1, 1, 0, new Size(150, 150), new Size(500, 500));

            for (int i = 0; i < facesDetectadas.size(); i++) {
                Rect dadosFace = facesDetectadas.get(0);
                rectangle(imagemColorida, dadosFace, new Scalar(0, 255, 0, 0));
                Mat faceCapturada = new Mat(imagemCinza, dadosFace);
                resize(faceCapturada, faceCapturada, new Size(160, 160));

                IntPointer rotulo = new IntPointer(1);
                DoublePointer confianca = new DoublePointer(1);
                reconhecedor.predict(faceCapturada, rotulo, confianca);
                predicao = rotulo.get(0);

                if (predicao == -1) {
                    nome = "Desconhecido";
                    rectangle(imagemColorida, dadosFace, new Scalar(0, 0, 255, 0));
                } else {
                    nome = pessoas[predicao] + " - " + confianca.get(0);
                }

                x = Math.max(dadosFace.tl().x() - 10, 0);
                y = Math.max(dadosFace.tl().y() - 10, 0);
                putText(imagemColorida, nome, new Point(x, y), FONT_HERSHEY_PLAIN, 1.4, new Scalar(0, 255, 255, 0) );
            }

            if (cFrame.isVisible()) {
                cFrame.showImage(frameCapturado);
            }

        }
        cFrame.dispose();
        camera.stop();
    }
}
