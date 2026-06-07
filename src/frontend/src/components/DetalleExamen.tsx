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

  return (
    <div className="list-container">
      <h2>Detalle del Examen: {detalle.alumno}</h2>
      <h3>Nota Final: {detalle.notaFinal}</h3>
      <button onClick={() => navigate('/examenes/corregir')} className="btn-edit" style={{marginBottom: '10px', marginRight: '10px'}}>Volver a Gestión</button>
      <button onClick={() => navigate('/dashboard')} className="btn-edit" style={{marginBottom: '10px'}}>Volver al Panel</button>
      <table>
        <thead>
          <tr>
            <th>Pregunta</th>
            <th>Respuesta Alumno</th>
            <th>Respuesta Correcta</th>
            <th>Resultado</th>
          </tr>
        </thead>
        <tbody>
          {detalle.preguntas.map((p: any, index: number) => (
            <tr key={index} style={{ backgroundColor: p.esCorrecta ? '#d4edda' : '#f8d7da' }}>
              <td>{p.enunciado}</td>
              <td>{p.respuestaElegida}</td>
              <td>{p.respuestaCorrecta}</td>
              <td>{p.esCorrecta ? 'Correcta' : 'Incorrecta'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default DetalleExamen;
