using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Polygon : MonoBehaviour
{

    
    public Vector2[] center = new Vector2[1];

    public  int radio;

    public  int n_sides;

    private LineRenderer line;



    // Start is called before the first frame update
    void Start()
    {
        line = gameObject.GetComponent<LineRenderer>();
        createShape();
    }

    void createShape()
    {
        line.positionCount = n_sides;
        calculatePositions();
        line.loop = true;

        line.startWidth = 0.5f;
        line.endWidth = 0.5f;
    }

    void calculatePositions()
    {   //Calcular primer punto
        Vector2 primer_punto = new Vector2(radio, 0) + center[0];
        line.SetPosition(0, primer_punto);

        //Calcular los siguientes puntos
        for (int i = 1; i < n_sides; i++)
        {
            float angle = 2 * Mathf.PI / n_sides * i;
            Vector2 npunto = new Vector2(Mathf.Cos(angle) * radio, Mathf.Sin(angle) * radio) + center[0];
            line.SetPosition(i, npunto);

        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
