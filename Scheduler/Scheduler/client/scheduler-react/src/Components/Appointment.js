// import { Link } from "react-router-dom";
// // import AuthContext from "../../contexts/AuthContext";

// function Appointment({ appointment }) {
//   // const { user } = useContext(AuthContext);

//   function formatTimeStamp(timestamp) {
//     // 2022-12-25T00:23:00.000+00:00
//     let formattedTime = "";
//     let year = timestamp.substring(0, 4);
//     let month = timestamp.substring(5, 7);
//     let day = timestamp.substring(8, 10);

//     let hour = timestamp.substring(11, 13);
//     let minute = timestamp.substring(14, 16);

//     formattedTime +=
//       year + "/" + month + "/" + day + " at " + hour + ":" + minute;

//     return formattedTime;
//   }

//   return (
//     <>
//       <tbody id="tableRows">
//         <tr key={appointment.appointmentId}>
//           <td>{appointment.customerName ? "Booked" : "Open"}</td>
//           <td>{formatTimeStamp(appointment.startTime)}</td>
//           <td>{formatTimeStamp(appointment.endTime)}</td>
//           <td className="buttonContainer">
//             {appointment.customerName ? (
//               ""
//             ) : (
//               <Link
//                 className="btn btn-secondary"
//                 to={`/book/${appointment.appointmentId}`}
//               >
//                 Book Appointment
//               </Link>
//             )}
//           </td>
//         </tr>
//       </tbody>
//     </>
//   );
// }

// export default Appointment;
