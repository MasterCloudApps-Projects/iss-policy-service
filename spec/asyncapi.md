# Policy events API definition 1.0.0 documentation

This document contains the definition and data structures of the events published by the Policy Service. The policy Service service is responsible to created offers, converted offers to insurance policies, and allowed for termination of policies as fact events to the published topic. The Policy Service publisher will generate policy event.
## Table of Contents

* [Servers](#servers)
* [Channels](#channels)

<a name="servers"></a>

## Servers

<table>
  <thead>
    <tr>
      <th>URL</th>
      <th>Protocol</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
  <tr>
      <td>localhost:9092</td>
      <td>kafka</td>
      <td>Allows you to connect in a local environment with kafka</td>
    </tr>
    <tr>
      <td colspan="3">
        <details>
          <summary>URL Variables</summary>
          <table>
            <thead>
              <tr>
                <th>Name</th>
                <th>Default value</th>
                <th>Possible values</th>
                <th>Description</th>
              </tr>
            </thead>
            <tbody>
              </tbody>
          </table>
        </details>
      </td>
    </tr>
    </tbody>
</table>




## Channels



<a name="channel-policy-topic"></a>


#### Channel Parameters




###  `publish` policy-topic



#### Message


Fact to propagate the policy event.



##### Payload


<table>
  <thead>
    <tr>
      <th>Name</th>
      <th>Type</th>
      <th>Description</th>
      <th>Accepted values</th>
    </tr>
  </thead>
  <tbody>

<tr>
  <td>payload </td>
  <td>object</td>
  <td><p>payload of the policy event</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.eventId </td>
  <td>string</td>
  <td><p>event id</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.eventTimestamp </td>
  <td>number</td>
  <td><p>event timestamp in millisecond</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.eventType </td>
  <td>string</td>
  <td><p>event type REGISTERED or TERMINATED</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policyId </td>
  <td>string</td>
  <td><p>policy id</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy </td>
  <td>object</td>
  <td><p>an object representing the policy</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.id </td>
  <td>string</td>
  <td><p>policy id</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.number </td>
  <td>string</td>
  <td><p>policy number</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.from </td>
  <td>string</td>
  <td><p>policy date from</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.to </td>
  <td>string</td>
  <td><p>policy date to</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.policyHolder </td>
  <td>string</td>
  <td><p>policy holder</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.productCode </td>
  <td>string</td>
  <td><p>policy holder</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.totalPremium </td>
  <td>number</td>
  <td><p>total premium policy</p>
 </td>
  <td><em>Any</em></td>
</tr>



<tr>
  <td>payload.policy.agentLogin </td>
  <td>string</td>
  <td><p>agent policy</p>
 </td>
  <td><em>Any</em></td>
</tr></tbody>
</table>


###### Example of payload _(generated)_

```json
{
  "payload": {
    "eventId": "d7d2d1d3-c8ff-421a-b8f7-d0cc2c972bfb",
    "eventTimestamp": 0,
    "eventType": "REGISTERED",
    "policyId": "d7d2d1d3-c8ff-421a-b8f7-d0cc2c972bfb",
    "policy": {
      "id": "d7d2d1d3-c8ff-421a-b8f7-d0cc2c972bfb",
      "number": "d7d2d1d3-c8ff-421a-b8f7-d0cc2c972bfb",
      "from": "2019-05-22",
      "to": "2020-05-22",
      "policyHolder": "François Poirier",
      "productCode": "CAR",
      "totalPremium": 150,
      "agentLogin": "admin"
    }
  }
}
```





