using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GradientLine : MonoBehaviour
{
    public float widthLine;

    public GameObject prefabsLine;

    public Transform parentLines;

    public Color color1, color2;

    private Color auxcolor;

    private float aux, ccy, auxlerp;


    // Start is called before the first frame update
    void Start()
    {

        Gradient(color1, color2);
    }

    void Gradient(Color color1, Color color2)
    {
        Camera camera = Camera.main;
        float height = 2f * camera.orthographicSize;
        float width = height * camera.aspect;

        //coordenadas
        float left = 0 - width / 2;
        float right = 0 + width / 2;

        int n_lineas = (int)Mathf.Ceil(height / widthLine);

        GameObject objLine1 = GameObject.Instantiate(prefabsLine);
        objLine1.transform.parent = parentLines;
        LineRenderer line1;
        line1 = objLine1.GetComponent<LineRenderer>();
        line1.material = new Material(Shader.Find("Legacy Shaders/Particles/Alpha Blended"));
        aux = height / 2 - widthLine / 2;
        Vector3[] positions1 = new Vector3[2];
        positions1[0] = new Vector3(left, aux, 0);
        positions1[1] = new Vector3(right, aux, 0);
        line1.positionCount = positions1.Length;
        line1.startColor = color1;
        line1.endColor = color1;
        line1.startWidth = widthLine;
        line1.endWidth = widthLine;
        line1.SetPositions(positions1);

        //Calcular distancia entre los puntos
        ccy = (float)1 / n_lineas;
        auxlerp = ccy;

        for (int i = 1; i < n_lineas; i++)
        {
            aux -= widthLine;
            ccy += auxlerp;
            GameObject objLine = GameObject.Instantiate(prefabsLine);
            objLine.transform.parent = parentLines;
            LineRenderer line;
            line = objLine.GetComponent<LineRenderer>();
            line.material = new Material(Shader.Find("Legacy Shaders/Particles/Alpha Blended"));
            Vector3[] positions = new Vector3[2];
            positions[0] = new Vector3(left, aux, 0);
            positions[1] = new Vector3(right, aux, 0);
            line.positionCount = positions.Length;
            auxcolor = Lerpcolor(color1, color2, ccy);
            line.startColor = auxcolor;
            line.endColor = auxcolor;
            line.startWidth = widthLine;
            line.endWidth = widthLine;
            line.SetPositions(positions);
        }

    }

    public static Color Lerpcolor(Color a, Color b, float t)
    {
        return new Color
        (
            a.r + (b.r - a.r) * t,
            a.g + (b.g - a.g) * t,
            a.b + (b.b - a.b) * t,
            a.a + (b.a - a.a) * t
        );
    }



    // Update is called once per frame
    void Update()
    {

    }
}
