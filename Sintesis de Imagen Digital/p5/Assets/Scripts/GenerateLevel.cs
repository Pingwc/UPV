using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GenerateLevel : MonoBehaviour
{

    public Texture2D image;

    public GameObject CubeWall;
    public GameObject Parent;

    private int _width, _height;
    private Color[] colourMap;


    // Start is called before the first frame update
    void Start()
    {
        Awake();
        detectarForma();
    }

    private void Awake()
    {
        _width = image.width;
        _height = image.height;

        colourMap = new Color[_width * _height];

    }

    private void detectarForma()
    {
        int x = 0;
        
        Color negro = new Color(0,0,0,1);

        for (int i = 0; i < _height; i++)
        {
            for (int j = 0; j < _width; j++)
            {
                colourMap[x] = image.GetPixel(i, j);
                if(colourMap[x] == negro)
                {
                    GameObject cubo = GameObject.Instantiate((CubeWall),
                        new Vector3(i, 0, j), Quaternion.identity,Parent.transform );

                }
            }
        }

    }



    // Update is called once per frame
    void Update()
    {
        
    }
}
