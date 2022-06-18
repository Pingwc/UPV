using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEditor;

public class ManagePixelsImage : MonoBehaviour
{

    public Texture2D image;

    public Color TargetColor;
    public Color PaintColor;

    public float Tolerance;


    private Texture2D copia;
    private int _width, _height;
    private Color[] colourMap;
    
    // Start is called before the first frame update

    void Start()
    {
        
        Awake();
        copia = Instantiate(image);
        cambiarColor();
       // image.Apply(true, false);


    }

    private void Awake()
    {
        _width = image.width;
        _height = image.height;

        colourMap = new Color[_width * _height];
        
    }

    private void cambiarColor()
    {
        Undo.RegisterCompleteObjectUndo(image, "desacer");
        float aux;
        int x = 0;
        for (int i = 0; i < _height; i++)
        {
            for (int j = 0; j < _width; j++)
            {
                colourMap[x] = image.GetPixel(i, j);
                aux = Vector4.Distance(colourMap[x], TargetColor);
                if(aux < Tolerance)
                {
                    image.SetPixel(i, j, PaintColor);
                }
                x++;
            }
        }

    }
    private void OnDisable()
    {
      
        image = copia;
        Debug.Log("PrintOnDisable: script was disabled");

        //No vuelve al color
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
