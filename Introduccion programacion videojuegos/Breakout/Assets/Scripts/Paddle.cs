using System.Collections;
using System.Collections.Generic;
using UnityEngine;
public class Paddle : MonoBehaviour
{
    public float speed = 3.0f;
    private const float leftLimit = -4.5f;
    private const float rightLimit = 4.5f;
    // Use this for initialization
    void Start()
    {
    }
    // Update is called once per frame
    void Update()
    {
        transform.Translate(Input.GetAxis("Horizontal") * Time.deltaTime * speed, 0, 0);
        transform.position = new Vector3(Mathf.Clamp(transform.position.x, leftLimit, rightLimit),
         transform.position.y,
        transform.position.z);
    }
}