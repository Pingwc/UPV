using UnityEngine;
using System.Collections;
public class Ball : MonoBehaviour
{
    private const float speedIncrement = 0.5f;
    private const float speedIncrementRate = 15.0f;
    Rigidbody rb;
    // Use this for initialization
    void Start()
    {
        RestartBall();
        StartCoroutine(IncreaseSpeed());
    }
    // Update is called once per frame
    void Update()
    {
    }
    void OnCollisionEnter(Collision collision)
    {
        if (collision.gameObject.tag == "Die")
            RestartBall();
    }
    void RestartBall()
    {
        rb = GetComponent<Rigidbody>();
        transform.position = new Vector3(0.0f, 0.25f, 0.0f);
        rb.velocity = Vector3.zero;
        rb.AddForce(-Random.Range(2.0f, 3.5f), 0.0f, -Random.Range(2.0f, 3.5f), ForceMode.Impulse);
    }
    IEnumerator IncreaseSpeed()
    {
        while (true)
        {
            rb.AddForce(rb.velocity.normalized * speedIncrement, ForceMode.Impulse);
            yield return new WaitForSeconds(speedIncrementRate);
        }
    }
}