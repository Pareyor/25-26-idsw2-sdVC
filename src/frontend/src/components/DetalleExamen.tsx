import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import examenService from '../services/examen.service';
import './Listas.css';

const DetalleExamen: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [detalle, setDetalle] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      examenService.getDetalleExamen(parseInt(id)).then(
        response => {
          setDetalle(response.data);
          setLoading(false);
        },
        error => {
          console.error(error);
          setLoading(false);
        }
      );
    }
  }, [id]);

  if (loading) return <div>Cargando...</div>;
  if (!detalle) return <div>No se encontró el detalle del examen.</div>;

  const esCorregido = detalle.preguntas.length > 0 && detalle.preguntas[0].respuestaElegida !== 'PENDIENTE';

  return (
    <div className="list-container">
      <h2>{esCorregido ? 'Detalle del Examen Corregido' : 'Contenido del Examen'} - {detalle.alumno}</h2>
      {esCorregido && <h3>Nota Final: {detalle.notaFinal}</h3>}
      <button onClick={() => navigate('/examenes/corregir')} className="btn-edit" style={{marginBottom: '10px', marginRight: '10px'}}>Volver a Gestión</button>
      <button onClick={() => navigate('/dashboard')} className="btn-edit" style={{marginBottom: '10px'}}>Volver al Panel</button>
      <table>
        <thead>
          <tr>
            <th>Pregunta</th>
            <th>Opciones de Respuesta</th>
            {esCorregido && <th>Respuesta Alumno</th>}
            {esCorregido && <th>Respuesta Correcta</th>}
            {esCorregido && <th>Resultado</th>}
          </tr>
        </thead>
        <tbody>
          {detalle.preguntas.map((p: any, index: number) => (
            <tr key={index} style={{ backgroundColor: esCorregido ? (p.esCorrecta ? '#d4edda' : '#f8d7da') : 'inherit' }}>
              <td>{p.enunciado}</td>
              <td>
                <ul style={{ margin: 0, paddingLeft: '1.2rem', fontSize: '0.9rem' }}>
                  {p.opciones && p.opciones.map((opt: string, i: number) => (
                    <li key={i}>{opt}</li>
                  ))}
                </ul>
              </td>
              {esCorregido && <td>{p.respuestaElegida}</td>}
              {esCorregido && <td>{p.respuestaCorrecta}</td>}
              {esCorregido && <td>{p.esCorrecta ? 'Correcta' : 'Incorrecta'}</td>}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DetalleExamen;
