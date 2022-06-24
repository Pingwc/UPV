using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Tempo : MonoBehaviour
{

    public float Tiempo;
    private int Tiempo1;

    public Text t_tempo;

    public AudioSource musica;
    public GameObject screen;
    // Start is called before the first frame update
    void Start()
    {
        Time.timeScale = 1;
    }

    // Update is called once per frame
    void Update()
    {
        Tiempo = Tiempo - Time.deltaTime;
        Tiempo1 = (int)Tiempo;
        t_tempo.text = Tiempo1.ToString();
        if (Tiempo < 20)
        {
            t_tempo.color = Color.red;
        }
        if (Tiempo < 0)
        {
            screen.SetActive(true);
            musica.Stop();
            Time.timeScale = 0;
        }

    }

}
