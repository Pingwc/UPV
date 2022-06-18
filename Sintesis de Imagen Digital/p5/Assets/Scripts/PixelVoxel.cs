using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PixelVoxel : MonoBehaviour
{
    public Texture2D image;

    public GameObject CubeWall;
    //public GameObject Parent;

    private int _width, _height;
    private Color[] colourMap;

    // Start is called before the first frame update
    void Start()
    {
        
        Awake();
        crearForma();
    }

    private void Awake()
    {
        _width = image.width;
        _height = image.height;

        colourMap = new Color[_width * _height];

    }

    private void crearForma()
    {
        int x = 0;


        for (int i = 0; i < _height; i++)
        {
            for (int j = 0; j < _width; j++)
            {
                colourMap[x] = image.GetPixel(i, j);

                if (colourMap[x].a != 0)
                {
                    GameObject cubo = GameObject.Instantiate((CubeWall),
                        new Vector3(i, j, 0), Quaternion.identity, transform);
                    cubo.GetComponent<MeshRenderer>().material.color = colourMap[x];
                }
            }
        }

    }

    // Update is called once per frame
    void Update()
    {
        
        if(Input.GetKeyDown(KeyCode.Space))
        {
            foreach(Transform children in transform)
            {
                int num1 =Random.Range(0, transform.childCount);
                int num2 = Random.Range(0, transform.childCount);

                Rigidbody rb = children.GetComponent<Rigidbody>();
                rb.useGravity = true;
                rb.AddExplosionForce(num1, children.position, num2, 2.0f);

            }
            
          
        }
    }
}
